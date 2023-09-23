package com.geekbrains.weatherforecast.repositories;

import com.geekbrains.weatherforecast.models.WeatherForecast;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ForecastRepository extends MongoRepository<WeatherForecast, String> {
    //Use the MongoRepository class in Spring to do the data operations on mangodb
    //already implies that we will use save, findall, findbyid, deletebyid etc.
}
