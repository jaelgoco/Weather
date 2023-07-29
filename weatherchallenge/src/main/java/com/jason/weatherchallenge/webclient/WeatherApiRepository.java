package com.jason.weatherchallenge.webclient;

import com.jason.weatherchallenge.model.dto.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WeatherApiRepository implements IWeatherApiRepository {

    private final WebClient webClient;

    @Value("${weather.api.webclient.key}")
    private String key;

    @Override
    public Mono<WeatherData> getWeatherData(String city, int days) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("forecast.json")
                        .queryParam("q", city)
                        .queryParam("days", days)
                        //.queryParam("hour", LocalTime.now().getHour())
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(WeatherData.class);
    }
}
