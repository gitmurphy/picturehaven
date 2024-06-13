package dev.petermurphy.picturehaven.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Environment: This server is used for testing and development purposes.\n" +
                "A different server should be used for production deployment.");

        Contact myContact = new Contact();
        myContact.setName("Peter Murphy");
        myContact.setEmail("petermurphy5672@gmail.com");

        Info information = new Info()
                .title("Picture Haven API")
                .version("1.0")
                .description("This API exposes endpoints to manage pictures, artists, tags, and their associations.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
