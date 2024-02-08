package br.com.pyetro.vendas.online.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;

@Configuration
public class ValidatorConfig {
	

	@Bean
    public Validator validatorFactory () {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    return bean;
    }
}
