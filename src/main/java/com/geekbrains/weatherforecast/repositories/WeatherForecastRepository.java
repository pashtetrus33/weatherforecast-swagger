package com.geekbrains.weatherforecast.repositories;

import com.geekbrains.weatherforecast.models.WeatherForecast;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class WeatherForecastRepository implements RepositoryInterface<WeatherForecast> {

    ForecastRepository forecastRepository;
    private static final Logger log = Logger.getLogger(WeatherForecastRepository.class.getName());

    public WeatherForecastRepository(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Override
    public List<WeatherForecast> getAll() {
        log.info("GetAll request");
        return forecastRepository.findAll();
    }

    @Override
    public List<WeatherForecast> getByDate(LocalDate date) {
        log.info("GetByDate request: " + date);
        return forecastRepository.findAll().stream().filter(p -> p.getDate().isEqual(date)).collect(Collectors.toList());
    }

    @Override
    public List<WeatherForecast> getByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        log.info("GetByPeriod request: " + dateFrom + " - " + dateTo);
        return forecastRepository.findAll().stream().filter(p -> (p.getDate().isAfter(dateFrom) || p.getDate().isEqual(dateFrom)) && (p.getDate().isBefore(dateTo) || p.getDate().isEqual(dateTo))).collect(Collectors.toList());
    }

    @Override
    public List<WeatherForecast> getByPeriodWithLocation(LocalDate dateFrom, LocalDate dateTo, String location) {
        log.info("GetByPeriodWithLocation request: " + dateFrom + " - " + dateTo + " Location: " + location);
        return getByPeriod(dateFrom, dateTo).stream().filter(p -> p.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());
    }

    @Override
    public Optional<WeatherForecast> getByDateAndLocation(LocalDate date, String location) {
        log.info("getByDateAndLocation request: " + date + " Location: " + location);
        return getByDate(date).stream().filter(p -> p.getLocation().equalsIgnoreCase(location)).findFirst();
    }

    @Override
    public boolean add(LocalDate date, String location, int value) {
        log.info("add request: " + date + " Location: " + location + " Temperature: " + value);
        if (getByDateAndLocation(date, location).isEmpty()) {
            forecastRepository.save(new WeatherForecast(date, location, value));
            return true;
        } else {
            log.info("add request uncompleted! Date: " + date + " and Location: " + location + " is already exists. Try update option.");
            return false;
        }
    }

    @Override
    public boolean delete(LocalDate date, String location) {
        log.info("delete request " + date + " Location: " + location);
        Optional<WeatherForecast> item = getByDateAndLocation(date, location);
        if (item.isPresent()) {
            forecastRepository.delete(item.get());
            return true;
        } else {
            log.info("delete request uncompleted! " + date + " Location: " + location);
            return false;
        }
    }

    @Override
    public boolean update(LocalDate date, String location, int newValue) {
        log.info("update request " + date + " Location: " + location + "Temperature: " + newValue);
        Optional<WeatherForecast> item = getByDateAndLocation(date, location);
        if (item.isPresent()) {
            WeatherForecast weatherForecast = item.get();
            forecastRepository.delete(weatherForecast);
            item.get().setTemperatureC(newValue);
            forecastRepository.insert(item.get());
            return true;
        } else {
            log.info("update request uncompleted! " + date + " Location: " + location + " is not found!");
            return false;
        }
    }
}
