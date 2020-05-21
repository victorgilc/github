package br.com.ateliware.github.service;

import java.util.List;
import java.util.stream.Collectors;

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
import br.com.ateliware.github.data.structure.GitHubTableId;
import br.com.ateliware.github.dto.GitHubDTO;

@Service
@Transactional
public class GitHubService {

	@Value("${github.url}")
	private String urlGitHub;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private GitHubRepository repository;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public GitHubDTO findRepository(String language) {

		ResponseEntity<GitHubDTO> response = restTemplate
				.exchange(buildUrlWithParameters(language).toString(), 
						HttpMethod.GET, null, GitHubDTO.class);
		
		List<GitHubTable> gitHubTableList = response.getBody().getItems().stream().map(item->{
			GitHubTable gitHubTable = new GitHubTable();
			GitHubTableId gitHubTableId = new GitHubTableId();
			gitHubTableId.setLanguage(language);
			gitHubTableId.setName(item.getName());
			gitHubTable.setId(gitHubTableId);
			return gitHubTable;
		}).collect(Collectors.toList());
		
		repository.saveAll(gitHubTableList);

		return response.getBody();
	}

	private StringBuilder buildUrlWithParameters(String language) {
		StringBuilder urlBuilder = new StringBuilder(urlGitHub);
		urlBuilder.append("language:");
		urlBuilder.append(language);
		return urlBuilder;
	}
}