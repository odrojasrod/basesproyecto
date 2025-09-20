package com.proyecto.joyeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

@SpringBootApplication
public class JoyeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoyeriaApplication.class, args);
	}

	@Bean
	CommandLineRunner testConnection(DataSource dataSource) {
		return args -> {
			try (var conn = dataSource.getConnection()) {
				System.out.println("Conectado a Oracle Autonomous Cloud! " + conn.getMetaData().getDatabaseProductName());
			}
		};
	}
}

