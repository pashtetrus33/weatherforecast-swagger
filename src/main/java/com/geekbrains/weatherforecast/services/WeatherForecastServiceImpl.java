package com.geekbrains.weatherforecast.services;

import com.geekbrains.weatherforecast.models.WeatherForecast;
import com.geekbrains.weatherforecast.repositories.RepositoryInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherForecastServiceImpl implements ForecastService<WeatherForecast> {

    RepositoryInterface<WeatherForecast> forecastRepository;

    public WeatherForecastServiceImpl(RepositoryInterface<WeatherForecast> forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Override
    public List<WeatherForecast> getAll() {
        return forecastRepository.getAll();
    }

    @Override
    public List<WeatherForecast> getByDate(LocalDate date) {
        return forecastRepository.getByDate(date);
    }

    @Override
    public List<WeatherForecast> getByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return forecastRepository.getByPeriod(dateFrom, dateTo);
    }

    @Override
    public List<WeatherForecast> getByPeriodWithLocation(LocalDate dateFrom, LocalDate dateTo, String location) {
        return forecastRepository.getByPeriodWithLocation(dateFrom, dateTo, location);
    }

    @Override
    public Optional<WeatherForecast> getByDateAndLocation(LocalDate date, String location) {
        return forecastRepository.getByDateAndLocation(date, location);
    }

    @Override
    public boolean add(LocalDate date, String location, int value) {
        return forecastRepository.add(date, location, value);
    }


    @Override
    public boolean delete(LocalDate date, String location) {
        return forecastRepository.delete(date, location);
    }

    @Override
    public boolean update(LocalDate date, String location, int newValue) {
        return forecastRepository.update(date, location, newValue);
    }
}
