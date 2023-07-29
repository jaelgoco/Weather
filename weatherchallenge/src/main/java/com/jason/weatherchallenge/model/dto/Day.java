package com.jason.weatherchallenge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Day {
    @JsonProperty("maxtemp_c")
    private double maxTempC;
    @JsonProperty("mintemp_c")
    private double minTempC;
    @JsonProperty("totalprecip_mm")
    private double totalPrecipMM;
    @JsonProperty("avghumidity")
    private int avgHumidity;
    @JsonProperty("condition")
    private Condition condition;
}
