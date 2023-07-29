package com.jason.weatherchallenge.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WeatherClient {

    @Bean
    public WebClient createClient() {
        return WebClient.builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .build();
    }
}
