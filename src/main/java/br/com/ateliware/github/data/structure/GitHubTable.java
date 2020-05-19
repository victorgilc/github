package br.com.ateliware.github.data.structure;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "github")
@Getter
@Setter
public class GitHubTable {
	@EmbeddedId
	private GitHubTableId id;
}
