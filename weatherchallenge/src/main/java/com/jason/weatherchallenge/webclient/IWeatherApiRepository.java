package com.jason.weatherchallenge.webclient;

import com.jason.weatherchallenge.model.dto.WeatherData;
import reactor.core.publisher.Mono;

public interface IWeatherApiRepository {
    Mono<WeatherData> getWeatherData(String city, int days);
}
