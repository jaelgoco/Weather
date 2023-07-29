package com.jason.weatherchallenge.service.util;

import com.jason.weatherchallenge.model.ResponseDto;
import com.jason.weatherchallenge.model.dao.Weather;
import com.jason.weatherchallenge.model.dto.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.jason.weatherchallenge.service.util.ServiceUtil.createErrorResponse;
import static com.jason.weatherchallenge.service.util.ServiceUtil.weatherBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class ServiceUtilTest {

    @Mock
    WebClientResponseException we;

    @Test
    void weatherBuilderTest() {
        String city = "madrid";
        ForecastDay forecastDay = new ForecastDay();
        Condition condition = new Condition("Sunny");
        forecastDay.setDate(LocalDate.now().toString());
        forecastDay.setDay(new Day(10.2, 10.3, 10.4, 20, condition));

        Weather expected = new Weather();
        expected.setDate(LocalDate.now().toString());
        expected.setCity("madrid");
        expected.setMaxTemp(10.2);
        expected.setMinTemp(10.3);
        expected.setTotalPrecipitation(10.4);
        expected.setAvgHumidity(20);
        expected.setCondition("Sunny");

        assertEquals(expected, weatherBuilder(forecastDay, city));
    }

    @Disabled
    @Test
    void createErrorResponse_OK() {
        WeatherErrorDto error = new WeatherErrorDto("1005", "Incorrect");
        WeatherResponseDto response = new WeatherResponseDto(error);

        //WebClientResponseException we = new WebClientResponseException(400, "", new HttpHeaders(), "{\"code\": 1002,\"message\": \"API key not provided\"}".getBytes(), StandardCharsets.UTF_8);

        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, "Incorrect"), BAD_REQUEST);

        assertEquals(expected, createErrorResponse(we));
    }
}