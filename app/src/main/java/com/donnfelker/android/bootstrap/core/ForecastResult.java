package com.donnfelker.android.bootstrap.core;

/**
 * Created by feather on 14-6-28.
 */
public class ForecastResult {

    private String currentCity;
    private WeatherData weather_data;


    public ForecastResult(String currentCity, WeatherData weather_data) {
        this.currentCity = currentCity;
        this.weather_data = weather_data;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public WeatherData getWeather_data() {
        return weather_data;
    }

    public void setWeather_data(WeatherData weather_data) {
        this.weather_data = weather_data;
    }
}
