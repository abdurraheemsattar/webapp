package com.springboot.webappdb.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    public OpenAPI springOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Spring OpenAPI ")
                        .description("Spring OpenAPI Documentation"));

    }

}


/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration() {
        // return a Docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/topics/*"))
                .apis(RequestHandlerSelectors.basePackage("com.springboot.webappdb"))
                .build();
    }
}
*/
