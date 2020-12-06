/**
 * @author : Oguz Kahraman
 * @since : 6.12.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

@Configuration
public class OpenAPIConfig {

    private static final String API_KEY = "sosu123iadsyuasuy";

    @Bean
    @Profile("test")
    public OpenAPI customOpenAPILive() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY, apiKeySecuritySchema())) // define the apiKey SecuritySchema
                .info(new Info().title("Suggesster App").description(
                        "SoSu API documentation"))
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)))
                .addServersItem(new Server().url("https://apij.suggestter.com/"));
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY, apiKeySecuritySchema())) // define the apiKey SecuritySchema
                .info(new Info().title("Suggesster App").description(
                        "SoSu API documentation"))
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY))); // then apply it. If you don't apply it will not be added to the header in cURL
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name("Authorization") // authorisation-token
                .description("Jwt value")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }


}

