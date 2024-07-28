package com.factorymethod.digitalwallet.config;

import com.factorymethod.digitalwallet.exception.CustomResponseErrorHandler;

import com.factorymethod.digitalwallet.util.RequestResponseHandlerInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;

import java.util.List;


@Configuration
public class RestTemplateConfiguration {

    // TODO: HttpClient and WebClient
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){

        RestTemplate restTemplate = restTemplateBuilder.
                setConnectTimeout(Duration.ofSeconds(3)).
                setReadTimeout(Duration.ofSeconds(3)).build();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RequestResponseHandlerInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    public WebClient myWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }

}


