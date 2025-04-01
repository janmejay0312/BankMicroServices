package com.janmejay.account;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//DockerTest@12

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(
		title = "Account MicroService REST API Documentation",
		description = "Account MicroService REST API Documentation",
		version = "v1",
		contact = @Contact(
				name = "Test",
				email ="Test@test.com",
				url = "http://localhost:8081/swagger-ui/index.html#/"
		)
))
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
