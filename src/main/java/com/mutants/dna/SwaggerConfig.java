package com.mutants.dna;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.tags(new Tag("DNAs","For mutants detection and statistics about them."))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mutants.dna.controller"))
				.paths(PathSelectors.ant("/dnachains/*")).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("DNA REST API")
				.description("Spring Boot REST API for accomplish Magneto´s mission.\n"
						+ "Detect mutants around the world to fight against the X-Mens.")
				.version("1.0")
				.contact(new Contact("Pablo Pagano", "https://www.linkedin.com/in/pablo-daniel-pagano-81178910a/",
						"pdpagano@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();

	}

}
