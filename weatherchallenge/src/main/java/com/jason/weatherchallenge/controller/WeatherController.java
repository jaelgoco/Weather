package com.jason.weatherchallenge.controller;

import com.jason.weatherchallenge.model.ResponseDto;
import com.jason.weatherchallenge.model.dao.Weather;
import com.jason.weatherchallenge.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/weather-forecast")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping("/{city}")
    public ResponseEntity<ResponseDto> createWeatherForecast(@PathVariable("city") String city,  @Value("${weather.controller.days}") String days) {
        return weatherService.saveWeatherForecast(city, Integer.parseInt(days));
    }

    @GetMapping("/{city}")
    public ResponseEntity<ResponseDto> getWeatherForecast(@PathVariable("city") String city, @Value("${weather.controller.days}") String days) {
        return weatherService.retrieveWeatherForecastByDaysAndCity(city, Integer.parseInt(days));
    }

    @GetMapping("/forecast")
    public List<List<Weather>> getAllWeatherForecast(@Value("${weather.controller.days}") String days) {
        return weatherService.retrieveAllWeatherForecastByDays(Integer.parseInt(days));
    }
}
