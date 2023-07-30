package com.jason.weatherchallenge.webclient;

import com.jason.weatherchallenge.model.dto.WeatherDto;
import reactor.core.publisher.Mono;

public interface IWeatherApiRepository {
    Mono<WeatherDto> getWeatherData(String city, int days);
}
