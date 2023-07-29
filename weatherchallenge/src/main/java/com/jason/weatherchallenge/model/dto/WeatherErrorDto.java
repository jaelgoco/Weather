package com.jason.weatherchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WeatherErrorDto {
    @JsonProperty("code")
    String code;
    @JsonProperty("message")
    String message;
}
