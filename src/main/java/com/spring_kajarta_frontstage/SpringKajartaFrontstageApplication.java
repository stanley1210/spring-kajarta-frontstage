package com.spring_kajarta_frontstage;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.crypto.SecretKey;

@SpringBootApplication(scanBasePackages = {"com.spring_kajarta_frontstage", "com.kajarta"})
@EntityScan(basePackages = "com.kajarta.demo.model")
public class SpringKajartaFrontstageApplication {


	public static void main(String[] args) {
//		System.out.println("test key="+Keys.secretKeyFor(SignatureAlgorithm.HS512));
		SpringApplication.run(SpringKajartaFrontstageApplication.class, args);
	}

}
