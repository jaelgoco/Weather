package com.jason.weatherchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jason.weatherchallenge.model.dao.Weather;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ResponseDto {
    @JsonProperty("forecasts")
    List<Weather> forecasts;
    @JsonProperty("errorMessage")
    private String errorMessage;
}
