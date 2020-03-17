package bluecitron.cleanblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableJpaAuditing
@SpringBootApplication
public class CleanblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanblogApplication.class, args);
	}

}
