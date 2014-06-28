package com.donnfelker.android.bootstrap.core;

/**
 * Created by feather on 14-6-28.
 */
public class Forecast {

    private int error;
    private String status;
    private String date;
    private ForecastResult results;

    public Forecast(int error, String status, String date, ForecastResult results) {
        this.error = error;
        this.status = status;
        this.date = date;
        this.results = results;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ForecastResult getResults() {
        return results;
    }

    public void setResults(ForecastResult results) {
        this.results = results;
    }
}
