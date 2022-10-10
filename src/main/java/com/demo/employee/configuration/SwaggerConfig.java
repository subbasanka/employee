package com.demo.employee.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select().build();
    }

//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("JavaInUse API")
//                .description("JavaInUse API reference for developers")
//                .termsOfServiceUrl("http://javainuse.com")
//                .contact(new Contact("Tech Interface", "https://www.youtube.com/channel/UCMpJ8m1w9t7EFcF9x8rs02A", "info@techinterface.com")).license("JavaInUse License")
//                .licenseUrl("javainuse@gmail.com").version("1.0").build();
//    }
}