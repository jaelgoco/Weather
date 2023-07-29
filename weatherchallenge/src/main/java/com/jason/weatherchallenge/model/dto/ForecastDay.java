package com.jason.weatherchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ForecastDay {
    @JsonProperty("date")
    String date;
    @JsonProperty("day")
    Day day;
}
