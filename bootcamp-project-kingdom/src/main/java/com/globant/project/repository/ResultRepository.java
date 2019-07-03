package com.globant.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.globant.project.model.Player;
import com.globant.project.model.Result;
import com.globant.project.pojo.TableResultPojo;

@RepositoryRestResource(collectionResourceRel = "results", path = "results")
public interface ResultRepository extends JpaRepository<Result, Long>{

	@Query("SELECT COUNT(re.game) FROM Result re WHERE re.player=?1 AND re.game = (SELECT COALESCE(MAX(r.game),0) FROM Result r WHERE r.player=?1)")
	Integer countMaxGameByUser(Player player);

	@Query("SELECT COALESCE(MAX(r.game),0) FROM Result r WHERE r.player=?1")
	Integer getMaxPlayerGame(Player player);
	
	@Query("SELECT NEW com.globant.project.pojo.TableResultPojo(re.player.id AS player, re.game, AVG(re.value) AS average) "
			+ "FROM Result re GROUP BY re.player.id, re.game ORDER BY re.game, re.player.id")
	List<TableResultPojo> generateFinalTable();

}
