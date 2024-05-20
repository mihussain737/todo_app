package com.todoapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "spring boot restapi documentation",
				description = "spring boot restapi todo app",
				version = "v1.0",
				contact = @Contact(
						name = "imam",
						email = "mihussain737@gmail.com"
				)
		)
)
public class TodoappApplication {

@Bean
public ModelMapper getModelMapper(){
	return new ModelMapper();
}
	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

}
