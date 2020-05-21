package br.com.ateliware.github.data.structure;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "github")
@Getter
@Setter
public class GitHubTable {
	@Id
	private Integer id;
	private String name;
	private String language;
}
