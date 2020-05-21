package br.com.ateliware.github.controller;

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
	public ResponseEntity<GitHubRepositoryDTO> findAndStore(@RequestBody String language) {
		return new ResponseEntity<>(gitHubService.findAndStore(language), 
				HttpStatus.OK);
	}
}
