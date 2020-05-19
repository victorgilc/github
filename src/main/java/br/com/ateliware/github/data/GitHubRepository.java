package br.com.ateliware.github.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ateliware.github.data.structure.GitHubTable;
import br.com.ateliware.github.data.structure.GitHubTableId;

@Repository
public interface GitHubRepository extends JpaRepository<GitHubTable, GitHubTableId>{
}
