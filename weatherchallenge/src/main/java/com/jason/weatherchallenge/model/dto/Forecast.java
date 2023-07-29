package com.jason.weatherchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Forecast {
    @JsonProperty("forecastday")
    List<ForecastDay> forecastDays;
}
