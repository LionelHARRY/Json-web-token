package com.springboot.jsonwebtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springboot.jsonwebtoken.domain.User;
import com.springboot.jsonwebtoken.domain.UserRepository;


@SpringBootApplication
public class JsonWebTokenApplication {
	@Autowired	
	private UserRepository urepository;	
	public static void main(String[] args) {
		SpringApplication.run(JsonWebTokenApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			//Username: user password: user
			urepository.save(new User("user", "$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi", "USER"));
			//Username: admin password: admin
			urepository.save(new User("admin", "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", "ADMIN"));
		};	
	}
}
