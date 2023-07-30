package com.jason.weatherchallenge.service;

import com.jason.weatherchallenge.model.dto.ResponseDto;
import com.jason.weatherchallenge.model.persistence.Weather;
import com.jason.weatherchallenge.model.dto.*;
import com.jason.weatherchallenge.repository.WeatherRepository;
import com.jason.weatherchallenge.webclient.WeatherApiRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    WeatherRepository weatherRepository;

    @Mock
    WeatherApiRepository weatherApiRepository;

    @InjectMocks
    WeatherService weatherService;

    String city = "madrid";
    int days = 2;
    String today = LocalDate.now().toString();

    @Test
    void saveWeatherForecastTest() {
        String city = "madrid";
        int days = 2;
        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, null), CREATED);

        when(weatherApiRepository.getWeatherData(city,days)).thenReturn(Mono.just(createWeatherData()));

        ResponseEntity<ResponseDto> result = weatherService.saveWeatherForecast(city, days);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
    }

    @Test
    void saveWeatherForecastTest_Failed() {
        String city = "madrid";
        int days = 2;
        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, "Failed to save weather information"), BAD_REQUEST);

        when(weatherApiRepository.getWeatherData(city,days)).thenReturn(Mono.empty());

        ResponseEntity<ResponseDto> result = weatherService.saveWeatherForecast(city, days);
        assertEquals(expected.getStatusCode(), result.getStatusCode());
    }

    @Test
    void persistForecast() {
    }

    @Test
    void retrieveWeatherForecastByDateAndCityTest() {
        Weather expected = new Weather(1L, LocalDate.now().toString(), "madrid", 10.8, 15.8, 0, 50.3, "Sunny");

        when(weatherRepository.findByDateAndCity(today, city)).thenReturn(createWeather());

        assertEquals(expected, weatherService.retrieveWeatherForecastByDateAndCity(today, city));
    }

    @Test
    void retrieveWeatherForecastByDaysAndCity() {
        List<Weather> weathers = new ArrayList<>();
        weathers.add(createWeather());
        weathers.add(createWeather());

        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(weathers, null), OK);

        when(weatherRepository.findByDateAndCity(today, city)).thenReturn(createWeather());

        ResponseEntity<ResponseDto> result = weatherService.retrieveWeatherForecastByDaysAndCity(city, days);

        assertEquals(expected.getStatusCode(), result.getStatusCode());
    }

    @Test
    void retrieveAllWeatherForecastByDays() {
        List<List<Weather>> expected = new ArrayList<>();
        List<Weather> weathers1 = new ArrayList<>();
        weathers1.add(createWeather());
        weathers1.add(createWeather());
        List<Weather> weathers2 = new ArrayList<>();
        weathers2.add(createWeather());
        weathers2.add(createWeather());
        expected.add(weathers1);
        expected.add(weathers2);

        List<Weather> testdata = new ArrayList<>();
        testdata.add(createWeather());
        testdata.add(createWeather());

        when(weatherRepository.findAllByDateOrderByCity(today)).thenReturn(testdata);

        List<List<Weather>> result = weatherService.retrieveAllWeatherForecastByDays(days);

        assertEquals(expected.size(), result.size());
        //assertEquals(expected.get(1).size(), result.get(1).size());
    }

    private WeatherDto createWeatherData() {
        Condition condition = new Condition("Sunny");
        Day day = new Day(10.2, 10.3, 10.4, 20, condition);
        ForecastDay forecastDay1 = new ForecastDay(LocalDate.now().toString(), day);
        ForecastDay forecastDay2 = new ForecastDay(LocalDate.now().plus(1, DAYS).toString(), day);
        List<ForecastDay> forecastDayList = new ArrayList<>();
        forecastDayList.add(forecastDay1);
        forecastDayList.add(forecastDay2);
        Forecast forecast = new Forecast(forecastDayList);
        Location location = new Location("madrid");
        return new WeatherDto(forecast, location);
    }

    private Weather createWeather() {
        return new Weather(1L, LocalDate.now().toString(), "madrid", 10.8, 15.8, 0, 50.3, "Sunny");
    }
}