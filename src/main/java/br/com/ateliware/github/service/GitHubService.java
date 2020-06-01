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
import br.com.ateliware.github.dto.GitHubDTO;
import br.com.ateliware.github.mapper.GitHubMapper;

@Service
@Transactional
public class GitHubService{
	
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

	public GitHubDTO findAndStore(String language) {
		ResponseEntity<GitHubDTO> response = restTemplate.exchange(buildUrlWithParameters(language).toString(),
				HttpMethod.GET, null, GitHubDTO.class);

		List<GitHubTable> gitHubTableList = response.getBody().getItems().stream().map(GitHubMapper::toTable)
				.collect(Collectors.toList());

		repository.deleteAll();
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