package br.com.ateliware.github.mapper;

import br.com.ateliware.github.data.structure.GitHubTable;
import br.com.ateliware.github.dto.GitHubItemDTO;

public class GitHubMapper {
	public static GitHubTable toTable(GitHubItemDTO dto) {
		GitHubTable table = new GitHubTable();
		table.setId(dto.getId());
		table.setLanguage(dto.getLanguage());
		table.setName(dto.getName());
		return table;
	}
	
	public static GitHubItemDTO toDto(GitHubTable table) {
		GitHubItemDTO dto = new GitHubItemDTO();
		table.setId(table.getId());
		table.setLanguage(table.getLanguage());
		table.setName(table.getName());
		return dto;
	}
}
