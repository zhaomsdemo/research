package com.zhaojh.research.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
@SecurityScheme(name = "Authorization", type= SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Research API")
                        .version("1.0.0")
                        .description("Research API"));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths().values().stream()
                    .flatMap(pathItem -> pathItem.readOperations().stream())
                    .forEach(operation -> {
                        headerParameters().forEach(headerParameter -> {
                            operation.addParametersItem(new HeaderParameter()
                                    .schema(new StringSchema()._default(headerParameter.example()))
                                    .description(headerParameter.description())
                                    .required(headerParameter.required())
                                    .name(headerParameter.name()));
                        });
                    });
        };
    }

    private List<HttpHeaderParam> headerParameters() {
        return List.of(
                new HttpHeaderParam("REQUEST-ID", "123", "The request id", false)
        );
    }
}
