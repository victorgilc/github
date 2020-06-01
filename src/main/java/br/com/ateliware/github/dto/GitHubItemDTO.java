package br.com.ateliware.github.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GitHubItemDTO {
	private Long id;
	private String name;
	private String language;
}
