package bg.tuvarna.ticketoffice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    public final static String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                    .name(SECURITY_SCHEME_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
            .info(new Info()
                .title("Ticket Office Rest APIs")
                .description("APIs for Ticket Office.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Ivaylo Ivanov")
                    .url("https://github.com/ivayloiv")
                    .email("ivvo98@gmail.com"))
                .license(new License()
                    .name("Apache License Version 2.0")
                    .url("https://github.com/moby/moby/blob/master/LICENSE")));
    }
}
