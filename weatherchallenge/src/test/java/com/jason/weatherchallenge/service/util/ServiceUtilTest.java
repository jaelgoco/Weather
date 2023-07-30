package com.jason.weatherchallenge.service.util;

import com.jason.weatherchallenge.model.dto.Condition;
import com.jason.weatherchallenge.model.dto.Day;
import com.jason.weatherchallenge.model.dto.ForecastDay;
import com.jason.weatherchallenge.model.dto.ResponseDto;
import com.jason.weatherchallenge.model.persistence.Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;

import static com.jason.weatherchallenge.service.util.ServiceUtil.createErrorResponse;
import static com.jason.weatherchallenge.service.util.ServiceUtil.weatherBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

    @Test
    void createErrorResponseTest() {
        ResponseEntity<ResponseDto> expected = new ResponseEntity<>(new ResponseDto(null, "Incorrect"), BAD_REQUEST);

        assertEquals(expected.getStatusCode(), createErrorResponse(we).getStatusCode());
    }
}