package com.globant.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.globant.project.model.House;
import com.globant.project.model.Pretender;

@RepositoryRestResource(collectionResourceRel = "pretenders", path = "pretenders")
public interface PretenderRepository extends JpaRepository<Pretender, Long> {

	@Query("SELECT p FROM Pretender p WHERE p.id = ?1")
	Pretender getById(Long id);

	@Query("SELECT p.house FROM Pretender p JOIN p.house h WHERE p.id=?1 AND h.id = ?2")
	House getPretenderHouse(Long idPretender, Long idHouse);
}
