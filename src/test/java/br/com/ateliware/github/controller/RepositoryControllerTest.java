package br.com.ateliware.github.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import br.com.ateliware.github.dto.GitHubRepositoryDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryControllerTest {

	@Autowired
	RepositoryController controller;

	@Test
	public void testDependencyInjection() {
		Assertions.assertTrue(controller != null);
	}

	@Test
	public void testNullLanguage() {
		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAll(null);
		});
	}

	@Test
	public void testNonexistentLanguages() {
		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAll("banana");
		});

		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAll("123");
		});

		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAll("!");
		});
	}

	@Test
	public void testLanguages() {
		testsForValidLanguages("assembly");
		testsForValidLanguages("c");
		testsForValidLanguages("c++");
		testsForValidLanguages("c#");
		testsForValidLanguages("java");
		testsForValidLanguages("groovy");
		testsForValidLanguages("python");
		testsForValidLanguages("ruby");
	}

	private void testsForValidLanguages(String language) {
		try {
			ResponseEntity<GitHubRepositoryDTO> response = controller.findAll(language);

			Assertions.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
			Assertions.assertTrue(response.getBody() != null);
			Assertions.assertTrue(response.getBody().getTotalCount() > 0);
			Assertions.assertTrue(response.getBody().getItems() != null);
			Assertions.assertTrue(!response.getBody().getItems().isEmpty());
		} catch (Exception e) {
			Assertions.assertThrows(HttpClientErrorException.class, () -> {
				controller.findAll(language);
			}, "403 rate limit exceeded");
		}
	}
}
