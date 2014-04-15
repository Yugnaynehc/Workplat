package com.donnfelker.android.bootstrap.core.inspect.result;

/**
 * Created by feather on 14-4-13.
 */
public class InspectEnvironment {
    private String date;
    private String temperature;
    private String humidity;
    private String personName;

    public InspectEnvironment(String date, String temperature, String humidity, String personName) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.personName = personName;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPersonName() {
        return personName;
    }
}
