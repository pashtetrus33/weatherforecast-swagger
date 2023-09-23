package com.geekbrains.weatherforecast.controllers;

import com.geekbrains.weatherforecast.models.WeatherForecast;
import com.geekbrains.weatherforecast.services.ForecastService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/weatherforecast")
public class WeatherForecastRestController {
    ForecastService<WeatherForecast> weatherForecastService;

    WeatherForecastRestController(ForecastService<WeatherForecast> weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping(value = "/getAll")
    public List<WeatherForecast> showAll() {
        return weatherForecastService.getAll();
    }

    @GetMapping(value = "/getByDate/{date}")
    public List<WeatherForecast> getByDate(@PathVariable(name = "date") String date) {
        return weatherForecastService.getByDate(LocalDate.parse(date));
    }

    @GetMapping(value = "/getByPeriod/{dateFrom}/{dateTo}")
    public List<WeatherForecast> getByPeriod(@PathVariable(name = "dateFrom") String dateFrom, @PathVariable(name = "dateTo") String dateTo) {
        return weatherForecastService.getByPeriod(LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
    }

    @GetMapping(value = "/getByPeriodWithLocation/{dateFrom}/{dateTo}/{location}")
    public List<WeatherForecast> getByPeriodWithLocation(@PathVariable(name = "dateFrom") String dateFrom, @PathVariable(name = "dateTo") String dateTo, @PathVariable(name = "location") String location) {
        return weatherForecastService.getByPeriodWithLocation(LocalDate.parse(dateFrom), LocalDate.parse(dateTo), location);
    }


    @GetMapping(value = "/getByDateAndLocation/{date}/{location}")
    public Optional<WeatherForecast> getByDate(@PathVariable(name = "date") String date, @PathVariable(name = "location") String location) {
        return weatherForecastService.getByDateAndLocation(LocalDate.parse(date), location);
    }

    @PostMapping(value = "/add/{date}/{location}/{value}")
    public boolean add(@PathVariable(name = "date") String date, @PathVariable(name = "location") String location, @PathVariable(name = "value") int value) {
        return weatherForecastService.add(LocalDate.parse(date), location, value);
    }

    @PutMapping(value = "/update/{date}/{location}/{value}")
    public boolean updateByDateAndLocation(@PathVariable(name = "date") String date, @PathVariable(name = "location") String location, @PathVariable(name = "value") int value) {
        return weatherForecastService.update(LocalDate.parse(date), location, value);
    }

    @DeleteMapping(value = "/delete/{date}/{location}")
    public boolean delete(@PathVariable(name = "date") String date, @PathVariable(name = "location") String location) {
        return weatherForecastService.delete(LocalDate.parse(date), location);
    }

    @GetMapping(value = "/getCurrentWeatherByCity/{city}")
    public String getCurrentWeatherByCity(@PathVariable(name = "city") String city) throws URISyntaxException, IOException {
        String apiEndPoint = "https://api.openweathermap.org/data/2.5/weather";
        StringBuilder requestBuilder = new StringBuilder(apiEndPoint);

        URIBuilder builder = new URIBuilder(apiEndPoint);
        builder.setParameter("q", city).setParameter("appid", System.getenv("API_KEY"));
        requestBuilder.append("?").append(builder);

        HttpGet get = new HttpGet(builder.build());

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = httpclient.execute(get);

        String rawResult = null;
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.printf("Bad response status code:%d%n",
                        response.getStatusLine().getStatusCode());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rawResult = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            response.close();
        }

        return rawResult;
    }

}
