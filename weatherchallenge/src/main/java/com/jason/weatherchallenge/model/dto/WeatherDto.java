package com.jason.weatherchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WeatherDto {
    @JsonProperty("forecast")
    Forecast forecast;
    @JsonProperty("location")
    Location location;
}
