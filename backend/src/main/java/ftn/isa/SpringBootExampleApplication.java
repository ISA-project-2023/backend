package ftn.isa;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
public class SpringBootExampleApplication {
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		return validatorFactory.getValidator();
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(SpringBootExampleApplication.class, args);

	}

}
