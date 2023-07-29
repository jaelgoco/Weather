package com.jason.weatherchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jason.weatherchallenge.model.dao.Weather;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WeatherResponseDto {
    @JsonProperty("error")
    WeatherErrorDto error;
}
