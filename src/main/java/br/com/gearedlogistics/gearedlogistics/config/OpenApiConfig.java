package br.com.gearedlogistics.gearedlogistics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadados exibidos no Swagger UI / OpenAPI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gearedLogisticsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Geared Logistics API")
                        .description("API de gestão logística")
                        .version("v1")
                        .contact(new Contact().name("Geared Logistics")));
    }
}
