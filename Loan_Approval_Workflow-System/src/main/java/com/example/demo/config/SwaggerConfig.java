package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Loan Approval Workflow API",
                version = "1.0",
                description = "Enterprise Loan Approval REST API"
        )
)
public class SwaggerConfig {

}
