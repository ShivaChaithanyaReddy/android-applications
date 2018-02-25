package com.example.first.weatherapp;

import java.io.Serializable;

/**
 * Created by Chaithanya on 4/7/2017.
 */

public class LocationAPI implements Serializable{

    long key;

    String city;
    String country;


    public LocationAPI() {}

    public LocationAPI(long key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "LocationAPI{" +
                "city='" + city + '\'' +
                ", key=" + key +
                ", country='" + country + '\'' +
                '}';
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
