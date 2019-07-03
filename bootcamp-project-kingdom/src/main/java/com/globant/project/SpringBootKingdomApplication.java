package com.globant.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.globant.project.enums.LevelEnum;
import com.globant.project.model.House;
import com.globant.project.model.Kingdom;
import com.globant.project.model.Player;
import com.globant.project.model.Pretender;
import com.globant.project.repository.HouseRepository;
import com.globant.project.repository.KingdomRepository;
import com.globant.project.repository.PlayerRepository;
import com.globant.project.repository.PretenderRepository;

@SpringBootApplication
@EnableJpaRepositories
public class SpringBootKingdomApplication {	
	
	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "production");
		SpringApplication.run(SpringBootKingdomApplication.class, args);		
	}
	
	@Bean
	CommandLineRunner runner (KingdomRepository kingdomRepository, PretenderRepository pretenderRepository, 
			HouseRepository houseRepository, PlayerRepository playerRepository) {
		return args ->{
			//Kingdoms
			kingdomRepository.save(new Kingdom("The North", "North"));//Winterfell
			kingdomRepository.save(new Kingdom("The Westerlands", "West"));//Casterly Rock
			kingdomRepository.save(new Kingdom("Bay of Dragons", "East"));//Dragonstone		
			
			//Houses
			houseRepository.save(new House("Stark", "Grey Direwolf", kingdomRepository.getById(1L)));
			houseRepository.save(new House("Bolton", "Flayed Man", kingdomRepository.getById(1L)));
			
			houseRepository.save(new House("Lannister", "Golden Lion", kingdomRepository.getById(2L)));
			
			houseRepository.save(new House("Targaryen", "Red Dragon", kingdomRepository.getById(3L)));
			
			//Pretenders
			pretenderRepository.save(new Pretender("Jon Snow", houseRepository.getById(1L), LevelEnum.PLATINUM));
			pretenderRepository.save(new Pretender("Ramsay Bolton", houseRepository.getById(2L), LevelEnum.GOLD));
			pretenderRepository.save(new Pretender("Jaime Lannister", houseRepository.getById(3L), LevelEnum.GOLD));
			
			//Players
			playerRepository.save(new Player("user", kingdomRepository.getById(1L)));
			playerRepository.save(new Player("david", kingdomRepository.getById(2L)));

		};
	}
}
