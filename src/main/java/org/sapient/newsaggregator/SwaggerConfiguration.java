package org.sapient.newsaggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String BASE_PACKAGE = "org.sapient.newsaggregator.controller";
    private static final String SERVICE_TITLE = "News Aggregator Service";
    private static final String SERVICE_DESCRIPTION = "This is detailed documentation of News Aggregator Service,"
            + " which is used to retrieve news from various providers.";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(regex("/api/v1.*")).build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title(SERVICE_TITLE).description(SERVICE_DESCRIPTION).build());

    }
}
