package com.example.main.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

// src/main/java/com/example/main/config/OpenAPIConfig.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("Village Portal API")
                                .version("v1")
                                .description("API documentation for Village Portal backend")
                                .contact(new Contact().name("Sangam Maurya").email("sangammaurya648@gmail.com"))
                        );
        }
}

