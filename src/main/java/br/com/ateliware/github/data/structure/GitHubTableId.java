package br.com.ateliware.github.data.structure;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class GitHubTableId implements Serializable{
	
	private static final long serialVersionUID = -4815032906218790855L;

	private String name;
	private String language;
}
