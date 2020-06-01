package br.com.ateliware.github.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ateliware.github.data.GitHubRepository;
import br.com.ateliware.github.data.structure.GitHubTable;
import br.com.ateliware.github.dto.GitHubDTO;
import br.com.ateliware.github.dto.GitHubItemDTO;
import br.com.ateliware.github.exception.NullParametersException;
import br.com.ateliware.github.mapper.GitHubMapper;
import br.com.ateliware.github.parameters.RepositoryParameters;

@Service
@Transactional
public class GitHubService {

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Value("${github.url}")
	private String urlGitHub;

	@Autowired
	private GitHubRepository repository;

	public GitHubDTO findAndStore(RepositoryParameters parameters) {
		validateParameters(parameters);
		
		ResponseEntity<GitHubDTO> response = fetchGithub(parameters);

		List<GitHubItemDTO> itemsThatAlreadyExists = repository.findByLanguage(parameters.getLanguage()).stream().map(GitHubMapper::toDto).collect(Collectors.toList());
		
		List<GitHubItemDTO> itemsThatDoesntExists = response.getBody().getItems().stream().filter(item->!itemsThatAlreadyExists.contains(item)).collect(Collectors.toList());
		
		saveDatabase(itemsThatAlreadyExists, itemsThatDoesntExists);
		
		GitHubDTO dto = mountDtoToReturn(response, itemsThatAlreadyExists, itemsThatDoesntExists);
		
		return dto;
	}

	private ResponseEntity<GitHubDTO> fetchGithub(RepositoryParameters parameters) {
		ResponseEntity<GitHubDTO> response = restTemplate.exchange(buildUrlWithParameters(parameters),
				HttpMethod.GET, null, GitHubDTO.class);
		return response;
	}

	private void saveDatabase(List<GitHubItemDTO> itemsThatAlreadyExists, List<GitHubItemDTO> itemsThatDoesntExists) {
		List<GitHubTable> toSave = itemsThatDoesntExists.stream().filter(item->!itemsThatAlreadyExists.contains(item)).map(GitHubMapper::toTable).collect(Collectors.toList());
		
		repository.saveAll(toSave);
	}

	private GitHubDTO mountDtoToReturn(ResponseEntity<GitHubDTO> response, List<GitHubItemDTO> itemsThatAlreadyExists,
			List<GitHubItemDTO> itemsThatDoesntExists) {
		GitHubDTO dto = new GitHubDTO();
		dto.setItems(Stream.concat(itemsThatAlreadyExists.stream(), itemsThatDoesntExists.stream())
                .collect(Collectors.toList()));
		dto.setTotalCount(response.getBody().getTotalCount());
		return dto;
	}

	private String buildUrlWithParameters(RepositoryParameters parameters) {
		StringBuilder urlBuilder = new StringBuilder(urlGitHub);
		urlBuilder.append("language:");
		urlBuilder.append(parameters.getLanguage());
		return urlBuilder.toString();
	}

	private void validateParameters(RepositoryParameters parameters) {
		if (parameters == null) {
			throw new NullParametersException();
		}
	}
}