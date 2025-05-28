package hr.tvz.napredna.java.dijezetserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Info information = new Info()
                .title("Di je ZET? - Crowdsource-based Public Transport Feedback Tool API")
                .version("1.0")
                .description("API exposes endpoints for analyzing and gathering feedback for public transport.");

        return new OpenAPI().info(information);
    }
}