package com.donnfelker.android.bootstrap.core.inspect;

import java.io.Serializable;

/**
 * Created by Feather on 14-6-28.
 */
public class Weather implements Serializable {
    private String date;
    private String sun;
    private String wind;
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
