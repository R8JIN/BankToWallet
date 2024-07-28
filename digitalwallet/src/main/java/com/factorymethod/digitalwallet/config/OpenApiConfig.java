package com.factorymethod.digitalwallet.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition (
        info = @Info (
                contact = @Contact (
                        name = "Rojin Awal's API",
                        email = "rojinawal23@gmail.com",
                        url = "https://github.com/R8JIN"

                ),
                description = "OpenAPI documentation for Bank to Wallet",
                title = "OpenAPI Specification - Bank to Wallet",
                version = "1.0",
                license = @License (
                        name = "MIT"
                )
        ),
        servers = {
                @Server (
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server (
                        description = "Local Demo ENV",
                        url = "http://demo.local:8080"
                )
        },
        security = {
        @SecurityRequirement(
                name = "bearerAuth"
        )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
