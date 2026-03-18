package com.seletoai;

import com.seletoai.adapters.persistence.role.SpringRoleRepository;
import com.seletoai.core.domain.role.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Bean
	public CommandLineRunner initDatabase(SpringRoleRepository roleRepository) {
		return args -> {
			System.out.println("Verificando perfis de acesso (Roles) no banco de dados...");

			if (roleRepository.findByName("FORNECEDOR").isEmpty()) {
				Role fornecedor = new Role();
				fornecedor.setName("FORNECEDOR");
				roleRepository.save(fornecedor);
				System.out.println("Role FORNECEDOR criada com sucesso.");
			}

			if (roleRepository.findByName("CONTRATANTE").isEmpty()) {
				Role contratante = new Role();
				contratante.setName("CONTRATANTE");
				roleRepository.save(contratante);
				System.out.println("Role CONTRATANTE criada com sucesso.");
			}
		};
	}
}