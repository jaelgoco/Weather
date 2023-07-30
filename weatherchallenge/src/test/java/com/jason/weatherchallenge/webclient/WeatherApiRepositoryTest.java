package com.jason.weatherchallenge.webclient;

import com.jason.weatherchallenge.model.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherApiRepositoryTest {

    @Mock
    WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @SuppressWarnings("rawtypes")
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @InjectMocks
    WeatherApiRepository weatherApiRepository;

    @Value("${weather.api.webclient.key}")
    String key;

    @Test
    @SuppressWarnings("unchecked")
    void getWeatherData() {
        String city = "madrid";
        int days = 2;
        WeatherDto expected = createWeatherData();

        ReflectionTestUtils.setField(weatherApiRepository, "key", "123");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(Mockito.any(Function.class));
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(WeatherDto.class)).thenReturn(Mono.just(createWeatherData()));
        WeatherDto result = weatherApiRepository.getWeatherData(city, days).block();

        assertNotNull(result);
        assertEquals(expected.getLocation().getName(), result.getLocation().getName());
        assertEquals(expected.getForecast(), result.getForecast());

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
}