package br.com.fiap.mercado_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Mercado API", version = "v1", description = "API do Mercado Java",
		contact = @Contact(name = "Cauan Aranega", email = "cauan@mercadoapi.com")))
public class MercadoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoApiApplication.class, args);
	}

}
