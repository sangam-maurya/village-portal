package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class VillagePortalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VillagePortalBackendApplication.class, args);
//		String hashpw = BCrypt.hashpw("Sangam", BCrypt.gensalt(5));
//		System.out.println(hashpw);
//		System.out.println(BCrypt.checkpw("Sangam", hashpw));
	}

}
