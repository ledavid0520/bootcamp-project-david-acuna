package com.globant.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.globant.project.model.Arena;

@RepositoryRestResource(collectionResourceRel = "arena", path = "arena")
public interface ArenaRepository extends JpaRepository<Arena, Long> {

}
