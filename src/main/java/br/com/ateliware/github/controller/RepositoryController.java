package br.com.ateliware.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ateliware.github.dto.GitHubRepositoryDTO;
import br.com.ateliware.github.service.GitHubService;

@RestController
@RequestMapping(value = "repository")
public class RepositoryController {
	
	@Autowired
	private GitHubService gitHubService;

	@GetMapping
	public ResponseEntity<GitHubRepositoryDTO> findAll(@RequestParam String language) {
		return new ResponseEntity<>(gitHubService.findRepository(language), 
				HttpStatus.OK);
	}
}
