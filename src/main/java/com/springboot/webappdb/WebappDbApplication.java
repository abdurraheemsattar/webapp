package com.springboot.webappdb;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class WebappDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappDbApplication.class, args);
    }


    public class SwaggerConfig {
        @Bean

        public OpenAPI springOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("Spring API Documentation")
                            .description("Spring Course Application")
                            .version("v0.0.1")
                            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
        }
    }


}
