package com.Lotus.polyFood;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@OpenAPIDefinition(
//		info = @Info(
//				title = "PolyFood Project",
//				version = "1.0.0",
//				description = "This project F&b food",
//				termsOfService = "Muan",
//				contact = @Contact(
//						name = "Muan",
//						email = "bunhinrom79@gmail.com"
//				),
//				license = @License(
//						name = "license",
//						url = "NguyenCOngManh"
//				)
//		)
//)
public class polyFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(polyFoodApplication.class, args);
	}

}
