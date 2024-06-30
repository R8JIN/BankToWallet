package com.rest.template.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;


@Configuration
public class RestTemplateConfiguration {

    // TODO: HttpClient and WebClient
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){

        RestTemplate restTemplate = restTemplateBuilder.
                setReadTimeout(Duration.ofSeconds(4)).
                setConnectTimeout(Duration.ofSeconds(4)).build();

//        restTemplate.setErrorHandler(null);
        return restTemplate;
    }

    @Bean
    public WebClient myWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }

}
