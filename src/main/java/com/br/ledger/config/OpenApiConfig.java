package com.br.ledger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI ledgerOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ledger Service API")
                                .description(
                                        "API ledger financeiro."
                                )
                                .version("1.0.0")
                );
    }
}