package com.globant.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.globant.project.model.Player;

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends JpaRepository<Player, Long> {
	
	@Query("SELECT p FROM Player p WHERE p.id = ?1")
	Player getById(Long id);
	
	Player findByName(String name);

}
