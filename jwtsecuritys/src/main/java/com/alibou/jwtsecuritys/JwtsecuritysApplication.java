package com.alibou.jwtsecuritys;

import com.alibou.jwtsecuritys.Entities.Role;
import com.alibou.jwtsecuritys.auth.AuthenticationService;
import com.alibou.jwtsecuritys.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.alibou.jwtsecuritys.Entities.Role.ADMIN;
import static com.alibou.jwtsecuritys.Entities.Role.MANAGER;

@SpringBootApplication
public class JwtsecuritysApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtsecuritysApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(

			AuthenticationService service
	)
	{
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("admin")
					.lastname("admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());





		};
	}

}
