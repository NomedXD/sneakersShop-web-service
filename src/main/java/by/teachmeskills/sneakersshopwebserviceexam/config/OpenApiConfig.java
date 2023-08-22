package by.teachmeskills.sneakersshopwebserviceexam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    // Basic controllers
    @Bean
    public GroupedOpenApi publicCategoryApi() {
        return GroupedOpenApi.builder()
                .group("categories")
                .pathsToMatch("/**/category/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicProductApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch("/**/product/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicCartApi() {
        return GroupedOpenApi.builder()
                .group("cart")
                .pathsToMatch("/**/cart/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicOrderApi() {
        return GroupedOpenApi.builder()
                .group("order")
                .pathsToMatch("/**/order/**")
                .build();
    }

    // Complex controllers

    @Bean
    public GroupedOpenApi publicAccountApi() {
        return GroupedOpenApi.builder()
                .group("account")
                .pathsToMatch("/**/account/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicAuthenticationApi() {
        return GroupedOpenApi.builder()
                .group("login")
                .pathsToMatch("/**/login/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicRegistrationApi() {
        return GroupedOpenApi.builder()
                .group("registration")
                .pathsToMatch("/**/registration/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicSearchApi() {
        return GroupedOpenApi.builder()
                .group("search")
                .pathsToMatch("/**/search/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application.description}") String appDescription,
                                 @Value("${application.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Sneakers shop-web-service")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache License Version 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact().name("Eleventh Studio")
                                .email("coovshow1@gmail.com")))
                .servers(List.of(new Server().url("http://localhost:8080/sneakersShop")
                                .description("Dev service"),
                        new Server().url("http://localhost:8082/sneakersShop")
                                .description("Beta service")));
    }
}

