package com.geekbrains.weatherforecast.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("weatherforecast")
public class WeatherForecast {

    //region private fields
    private String id;
    private LocalDate date;
    private String location;
    private int temperatureC;
    private int temperatureF;
    //endregion

    public WeatherForecast(LocalDate date, String location, int temperatureC) {
        this.date = date;
        this.location = location;
        this.temperatureC = temperatureC;
        this.temperatureF = 32 + (int) (temperatureC * 9 / 5);
    }

    public WeatherForecast() {
    }

    //region getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(int temperatureC) {
        this.temperatureC = temperatureC;
        this.temperatureF = 32 + (int) (temperatureC * 9 / 5);
    }

    public int getTemperatureF() {
        return temperatureF;
    }
    //endregion
}
