package com.jason.weatherchallenge.service;

import com.jason.weatherchallenge.model.dto.ResponseDto;
import com.jason.weatherchallenge.model.dto.ForecastDay;
import com.jason.weatherchallenge.model.dto.WeatherDto;
import com.jason.weatherchallenge.model.persistence.Weather;
import com.jason.weatherchallenge.model.persistence.repository.WeatherRepository;
import com.jason.weatherchallenge.webclient.repository.WeatherApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.jason.weatherchallenge.service.util.ServiceUtil.createErrorResponse;
import static com.jason.weatherchallenge.service.util.ServiceUtil.weatherBuilder;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherApiRepository weatherApiRepository;

    public ResponseEntity<ResponseDto> saveWeatherForecast(String city, int days) {
        WeatherDto weatherDto;
        try {
            weatherDto = weatherApiRepository.getWeatherData(city, days).block();
        }catch (WebClientResponseException we) {
            return createErrorResponse(we);
        }

        if(weatherDto != null) {
            List<ForecastDay> forecastDayList = weatherDto.getForecast().getForecastDays();
            ResponseEntity<ResponseDto> response = persistForecast(forecastDayList, weatherDto.getLocation().getName());
            if (response != null) {
                return response;
            }
            return new ResponseEntity<>(new ResponseDto(null, null), CREATED);
        }
        return new ResponseEntity<>(new ResponseDto(null, "Failed to save weather information"), BAD_REQUEST);
    }

    public ResponseEntity<ResponseDto> persistForecast(List<ForecastDay> forecastDayList, String city) {
        try {
            forecastDayList.forEach(forecastDay -> {
                if(retrieveWeatherForecastByDateAndCity(forecastDay.getDate(), city) != null) {
                    log.info("Unable to save forecast for {} with date {} as it already exists", city, forecastDay.getDate());
                } else {
                    weatherRepository.save(weatherBuilder(forecastDay, city.toLowerCase()));
                }
            });
        }catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(null, "Failed to save weather information"), BAD_REQUEST);
        }
        return null;
    }

    public Weather retrieveWeatherForecastByDateAndCity(String date, String city) {
        return weatherRepository.findByDateAndCity(date, city.toLowerCase());
    }

    public ResponseEntity<ResponseDto> retrieveWeatherForecastByDaysAndCity(String city, int days) {
        List<Weather> weathers = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i=0; i < days; i++) {
            weathers.add(retrieveWeatherForecastByDateAndCity(today.plus(i, DAYS).toString(), city));
        }

        if(weathers.stream().allMatch(Objects::isNull)) {
            return new ResponseEntity<>(new ResponseDto(null, "No forecasts existing for city " + city), NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto(weathers, null), OK);
    }

    public List<List<Weather>> retrieveAllWeatherForecastByDays(int days) {
        List<List<Weather>> weathers = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i=0; i < days; i++) {
            weathers.add(weatherRepository.findAllByDateOrderByCity(today.plus(i, DAYS).toString()));
        }

        return weathers;
    }

}
