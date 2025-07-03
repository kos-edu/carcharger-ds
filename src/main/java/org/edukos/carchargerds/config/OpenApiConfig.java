package org.edukos.carchargerds.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI carChargerOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Car Charger DS API")
                        .description("API for managing electric vehicle charging status")
                        .version("1.0.0"));
    }
}