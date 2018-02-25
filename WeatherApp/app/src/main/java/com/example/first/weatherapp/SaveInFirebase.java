package com.example.first.weatherapp;

import java.io.Serializable;

/**
 * Created by Chaithanya on 4/8/2017.
 */

public class SaveInFirebase implements Serializable,Comparable {

    String key;
    String city;
    String country;
    String tempearture;
    String time;
    boolean favorite;


    @Override
    public int compareTo(Object another) {
        if(((SaveInFirebase)another).isFavorite() == true){
            return 1;
        }else{
            return 0;
        }
    }


    public SaveInFirebase() {

    }

    public SaveInFirebase(String country, String time, String key, boolean favorite, String tempearture, String city) {
        this.city = city;
        this.country = country;
        this.favorite = favorite;
        this.key = key;
        this.tempearture = tempearture;
        this.time = time;
    }


    @Override
    public String toString() {
        return "SaveInFirebase{" +
                "city='" + city + '\'' +
                ", key='" + key + '\'' +
                ", country='" + country + '\'' +
                ", tempearture='" + tempearture + '\'' +
                ", time='" + time + '\'' +
                ", favorite=" + favorite +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTempearture() {
        return tempearture;
    }

    public void setTempearture(String tempearture) {
        this.tempearture = tempearture;
    }
}
