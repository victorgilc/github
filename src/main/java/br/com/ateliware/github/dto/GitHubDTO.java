package br.com.ateliware.github.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubDTO {
	@JsonProperty("total_count")
	private Long totalCount;
	private List<GitHubItemDTO> items;
}
