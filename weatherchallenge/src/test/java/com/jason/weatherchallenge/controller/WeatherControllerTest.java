package com.jason.weatherchallenge.controller;

import com.jason.weatherchallenge.model.persistence.Weather;
import com.jason.weatherchallenge.model.dto.ResponseDto;
import com.jason.weatherchallenge.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    WeatherService weatherService;

    @InjectMocks
    WeatherController weatherController;

    String city = "madrid";
    String days = "2";

    @Test
    void createWeatherForecastTest_Created() {
        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, null), CREATED);

        when(weatherService.saveWeatherForecast(city, Integer.parseInt(days)))
                .thenReturn(new ResponseEntity<>(new ResponseDto(null, null), CREATED));

        assertEquals(expected.getStatusCode(), weatherController.createWeatherForecast(city, days).getStatusCode());
    }

    @Test
    void createWeatherForecastTest_BadRequest() {
        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, null), BAD_REQUEST);

        when(weatherService.saveWeatherForecast(city, Integer.parseInt(days)))
                .thenReturn(new ResponseEntity<>(new ResponseDto(null, null), BAD_REQUEST));

        assertEquals(expected.getStatusCode(), weatherController.createWeatherForecast(city, days).getStatusCode());
    }

    @Test
    void getWeatherForecastTest_OK() {
        List<Weather> testData = new ArrayList<>();
        testData.add(createWeather());
        testData.add(createWeather());

        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(testData, null), OK);

        when(weatherService.retrieveWeatherForecastByDaysAndCity(city, Integer.parseInt(days)))
                .thenReturn(new ResponseEntity<>(new ResponseDto(testData, null), OK));

        assertEquals(expected.getStatusCode(), weatherController.getWeatherForecast(city, days).getStatusCode());
    }

    @Test
    void getWeatherForecastTest_NotFound() {
        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, "No forecasts existing for disneyland "), NOT_FOUND);

        when(weatherService.retrieveWeatherForecastByDaysAndCity(city, Integer.parseInt(days)))
                .thenReturn(new ResponseEntity<>(new ResponseDto(null, "No forecasts existing for disneyland "), NOT_FOUND));

        assertEquals(expected.getStatusCode(), weatherController.getWeatherForecast(city, days).getStatusCode());
    }

    @Test
    void getAllWeatherForecastTest() {
        List<List<Weather>> expected = new ArrayList<>();
        List<Weather> weathers1 = new ArrayList<>();
        weathers1.add(createWeather());
        weathers1.add(createWeather());
        List<Weather> weathers2 = new ArrayList<>();
        weathers2.add(createWeather());
        weathers2.add(createWeather());
        expected.add(weathers1);
        expected.add(weathers2);

        List<List<Weather>> testdata = new ArrayList<>();
        testdata.add(weathers1);
        testdata.add(weathers2);

        when(weatherService.retrieveAllWeatherForecastByDays(Integer.parseInt(days))).thenReturn(testdata);

        List<List<Weather>> result = weatherController.getAllWeatherForecast(days);

        assertEquals(expected, result);
        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0).size(), result.get(0).size());
        assertEquals(expected.get(1).size(), result.get(1).size());
    }

    private Weather createWeather() {
        return new Weather(1L, LocalDate.now().toString(), "madrid", 10.8, 15.8, 0, 50.3, "Sunny");
    }
}