package com.pjestudos.pjfood;

import com.pjestudos.pjfood.api.domain.repository.impl.CustomJpaRepositoryIml;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryIml.class)
public class PjfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjfoodApiApplication.class, args);
	}

}
