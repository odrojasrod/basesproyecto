package com.proyecto.joyeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

@SpringBootApplication
public class JoyeriaApplication {

	public static void main(String[] args) {
		// Configurar propiedades del sistema para Oracle Cloud Wallet
		System.setProperty("oracle.net.tns_admin", "D:/basesproyecto/src/main/resources/Wallet_proyecto");
		System.setProperty("oracle.net.ssl_server_dn_match", "true");
		System.setProperty("oracle.net.ssl_version", "1.2");

		// Configurar truststore si existe
		String trustStorePath = "D:/basesproyecto/src/main/resources/Wallet_proyecto/truststore.jks";
		java.io.File trustStore = new java.io.File(trustStorePath);
		if (trustStore.exists()) {
			System.setProperty("javax.net.ssl.trustStore", trustStorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		}

		SpringApplication.run(JoyeriaApplication.class, args);
	}

	@Bean
	CommandLineRunner testConnection(DataSource dataSource) {
		return args -> {
			try (var conn = dataSource.getConnection()) {
				System.out.println("Conectado a Oracle Autonomous Cloud! " + conn.getMetaData().getDatabaseProductName());
			} catch (Exception e) {
				System.err.println("Error conectando a la base de datos: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}
}