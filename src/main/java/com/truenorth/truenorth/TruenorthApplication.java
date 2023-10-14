package com.truenorth.truenorth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server( url = "/api/v1", description = "server")})
public class TruenorthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruenorthApplication.class, args);
	}

}
