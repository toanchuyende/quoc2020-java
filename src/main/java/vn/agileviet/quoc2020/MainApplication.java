package vn.agileviet.quoc2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}


//	@Bean
//	public ValidatingMongoEventListener validatingMongoEventListener() {
//		return new ValidatingMongoEventListener(validator());
//	}
//
//	@Bean
//	public LocalValidatorFactoryBean validator() {
//		return new LocalValidatorFactoryBean();
//	}
}
