package com.jason.weatherchallenge.service.util;

import com.jason.weatherchallenge.model.ResponseDto;
import com.jason.weatherchallenge.model.dao.Weather;
import com.jason.weatherchallenge.model.dto.Day;
import com.jason.weatherchallenge.model.dto.ForecastDay;
import com.jason.weatherchallenge.model.dto.WeatherResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ServiceUtil {
    public static Weather weatherBuilder(ForecastDay forecastDay, String city) {
        Day day = forecastDay.getDay();

        return Weather.builder()
                .city(city)
                .date(forecastDay.getDate())
                .maxTemp(day.getMaxTempC())
                .minTemp(day.getMinTempC()).
                avgHumidity(day.getAvgHumidity())
                .totalPrecipitation(day.getTotalPrecipMM())
                .condition(day.getCondition().getText())
                .build();
    }

    public static ResponseEntity<ResponseDto> createErrorResponse(WebClientResponseException we) {
        WeatherResponseDto errorResponse = we.getResponseBodyAs(WeatherResponseDto.class);

        if(errorResponse != null) {
            return new ResponseEntity<>(new ResponseDto(null, errorResponse.getError().getMessage()), BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ResponseDto(null,"Bad Request"), BAD_REQUEST);
        }
    }
}
