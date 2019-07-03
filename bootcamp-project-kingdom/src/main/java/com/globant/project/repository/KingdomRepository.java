package com.globant.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.globant.project.model.House;
import com.globant.project.model.Kingdom;

@RepositoryRestResource(collectionResourceRel = "kingdoms", path = "kingdoms")
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {
	
	@Query("SELECT k FROM Kingdom k WHERE k.id = ?1")
	Kingdom getById(Long id);

	@Query("SELECT k.houses FROM Kingdom k WHERE k.id = ?1")
	List<House> getKingdomHouses(Long id);
	
}
