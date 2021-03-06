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

import br.com.ateliware.github.dto.GitHubDTO;
import br.com.ateliware.github.exception.NullParametersException;
import br.com.ateliware.github.parameters.RepositoryParameters;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryControllerTest {

	@Autowired
	private RepositoryController controller;

	@Test
	public void testDependencyInjection() {
		Assertions.assertTrue(controller != null);
	}

	@Test
	public void testNullParameters() {
		Assertions.assertThrows(NullParametersException.class, () -> {
			controller.findAndStore(null);
		});
	}

	@Test
	public void testNonexistentLanguages() {
		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAndStore(buildLanguageParameter("banana"));
		});

		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAndStore(buildLanguageParameter("123"));
		});

		Assertions.assertThrows(HttpClientErrorException.class, () -> {
			controller.findAndStore(buildLanguageParameter("!"));
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
			ResponseEntity<GitHubDTO> response = controller.findAndStore(buildLanguageParameter(language));

			Assertions.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
			Assertions.assertTrue(response.getBody() != null);
			Assertions.assertTrue(response.getBody().getTotalCount() > 0);
			Assertions.assertTrue(response.getBody().getItems() != null);
			Assertions.assertTrue(!response.getBody().getItems().isEmpty());
		} catch (Exception e) {
			Assertions.assertThrows(HttpClientErrorException.class, () -> {
				controller.findAndStore(buildLanguageParameter(language));
			}, "403 rate limit exceeded");
		}
	}
	
	private RepositoryParameters buildLanguageParameter(String language){
		RepositoryParameters parameters = new RepositoryParameters();
		parameters.setLanguage(language);
		return parameters;
	}
}
