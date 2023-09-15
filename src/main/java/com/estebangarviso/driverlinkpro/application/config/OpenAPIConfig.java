package com.estebangarviso.driverlinkpro.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080/");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setEmail("e.garvisovenegas@gmail.com");
        contact.setName("Esteban Garviso");
        contact.setUrl("https://github.com/estebangarviso");

        Info info = new Info()
                .title("DriverEntity Link Pro API")
                .version("1.0")
                .contact(contact)
                .description("Documentation for DriverEntity Link Pro API")
                .termsOfService("https://github.com/estebangarviso/Api.DriverLinkPro");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {

        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
    }
}

