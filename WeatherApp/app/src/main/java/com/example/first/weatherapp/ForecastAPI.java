package com.example.first.weatherapp;

import java.io.Serializable;

/**
 * Created by Chaithanya on 4/7/2017.
 */

public class ForecastAPI implements Serializable{

    String DateTime;
    String weatherText;
    String WeatherIcon;
    String metric;


    public ForecastAPI(String dateTime, String weatherIcon, String weatherText, String metric) {
        DateTime = dateTime;
        WeatherIcon = weatherIcon;
        this.weatherText = weatherText;
        this.metric = metric;
    }

    @Override
    public String toString() {
        return "ForecastAPI{" +
                "DateTime='" + DateTime + '\'' +
                ", weatherText='" + weatherText + '\'' +
                ", WeatherIcon='" + WeatherIcon + '\'' +
                ", metric='" + metric + '\'' +
                '}';
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getWeatherIcon() {
        return WeatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}
