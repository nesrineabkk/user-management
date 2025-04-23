package es.ibm.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "es.ibm.usermanagement")
public class UserManagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
