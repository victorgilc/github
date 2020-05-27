package br.com.ateliware.github.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ateliware.github.dto.GitHubDTO;
import br.com.ateliware.github.service.GitHubService;

@RestController
@RequestMapping(value = "repository")
public class RepositoryController {

	@Autowired
	private GitHubService gitHubService;

	@PostMapping
	public ResponseEntity<GitHubDTO> findAndStore(@RequestBody Map<String, Object> parameters) {
		return new ResponseEntity<>(
				gitHubService.findAndStore(parameters != null ? parameters.get("language").toString() : null),
				HttpStatus.OK);
	}
}
