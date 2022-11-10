package com.assignment.telia;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        // NOTE: set the package of your application to avoid showing the spring-boot default services in the swagger documentation
        List<VendorExtension> vendorExtensions = new ArrayList<>();

        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(new ApiInfo("Web-API For User Management System", "In the User Management System you can Create USER, Update the details of User, Get the information of User, Delete the User\n", "0.0.1", "", new Contact("Prashant Borkar", "https://prashantborkar.io", "pfborkar@gmail.com"), "Logical Support by Prashant", "", vendorExtensions)).select().apis(RequestHandlerSelectors.basePackage("com.assignment.telia")).paths(PathSelectors.any()).build();
    }
}