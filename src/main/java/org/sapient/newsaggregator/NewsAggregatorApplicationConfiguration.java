package org.sapient.newsaggregator;

import org.sapient.newsaggregator.client.RestTemplateResponseErrorHandler;
import org.sapient.newsaggregator.dto.SearchResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
@ComponentScan(basePackages = {"org.sapient.newsaggregator"})
@EnableAsync
public class NewsAggregatorApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Bean
    public HashMap<String, ArrayList<SearchResponse>> newsAggregatorHashMap() {
        return new HashMap<>();
    }
}
