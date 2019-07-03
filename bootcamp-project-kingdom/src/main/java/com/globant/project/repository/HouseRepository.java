package com.globant.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.globant.project.model.House;
import com.globant.project.model.Pretender;

@RepositoryRestResource(collectionResourceRel = "houses", path = "houses")
public interface HouseRepository extends JpaRepository<House, Long> {

	@Query("SELECT h FROM House h WHERE h.id = ?1")
	House getById(Long id);

	@Query("SELECT h.pretenders FROM House h WHERE h.id = ?1")
	List<Pretender> getHousePretenders(Long houseId);

	@Query("SELECT h FROM House h WHERE h.id = ?1")
	Boolean houseCreated(Long houseId);
}
