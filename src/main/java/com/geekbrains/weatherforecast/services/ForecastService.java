package com.geekbrains.weatherforecast.services;

import com.geekbrains.weatherforecast.models.WeatherForecast;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ForecastService<T> {
    public List<T> getAll();

    public List<T> getByDate(LocalDate date);

    public List<T> getByPeriod(LocalDate dateFrom, LocalDate dateTo);

    public List<T> getByPeriodWithLocation(LocalDate dateFrom, LocalDate dateTo, String location);

    public Optional<T> getByDateAndLocation(LocalDate date, String location);

    public boolean add(LocalDate date, String location, int value);

    public boolean delete(LocalDate date, String location);

    public boolean update(LocalDate date, String location, int newValue);
}

