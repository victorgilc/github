package br.com.ateliware.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ateliware.github.dto.GitHubRepositoryDTO;

@Service
public class GitHubService {

	@Value("${github.url}")
	private String urlGitHub;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public GitHubRepositoryDTO findRepository(String language) {

		ResponseEntity<GitHubRepositoryDTO> response = restTemplate
				.exchange(buildUrlWithParameters(language).toString(), 
						HttpMethod.GET, null, GitHubRepositoryDTO.class);

		return response.getBody();
	}

	private StringBuilder buildUrlWithParameters(String language) {
		StringBuilder urlBuilder = new StringBuilder(urlGitHub);
		urlBuilder.append("language:");
		urlBuilder.append(language);
		return urlBuilder;
	}
}