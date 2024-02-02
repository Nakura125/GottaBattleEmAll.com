package com.GottaBattleEmAll.GottaBattleEmAll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GottaBattleEmAllApplication {

	public static void main(String[] args) {
		SpringApplication.run(GottaBattleEmAllApplication.class, args);
	}






}
