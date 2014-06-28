package com.donnfelker.android.bootstrap.core;

/**
 * Created by feather on 14-6-28.
 */
public class WeatherData {

    private String[] data;
    private String[] weather;
    private String[] wind;
    private String[] temperature;

    public WeatherData(String[] data, String[] weather, String[] wind, String[] temperature) {
        this.data = data;
        this.weather = weather;
        this.wind = wind;
        this.temperature = temperature;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String[] getWeather() {
        return weather;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public String[] getWind() {
        return wind;
    }

    public void setWind(String[] wind) {
        this.wind = wind;
    }

    public String[] getTemperature() {
        return temperature;
    }

    public void setTemperature(String[] temperature) {
        this.temperature = temperature;
    }
}
