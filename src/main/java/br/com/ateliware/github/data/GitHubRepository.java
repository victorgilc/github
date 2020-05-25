package br.com.ateliware.github.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ateliware.github.data.structure.GitHubTable;

@Repository
public interface GitHubRepository extends JpaRepository<GitHubTable, Long>{
	
	List<GitHubTable> findByLanguage(String language);
}
