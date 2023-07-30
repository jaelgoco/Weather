package com.jason.weatherchallenge.repository;

import com.jason.weatherchallenge.model.persistence.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findAllByDateOrderByCity(String date);
    Weather findByDateAndCity(String date, String city);
}
