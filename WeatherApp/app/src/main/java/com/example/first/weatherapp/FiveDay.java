package com.example.first.weatherapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 4/8/2017.
 */

public class FiveDay  implements Serializable{

    String Headline;
    String Headline_link;
    ArrayList<DailyCast> arrayList;

    public String getHeadline_link() {
        return Headline_link;
    }

    @Override
    public String toString() {
        return "FiveDay{" +
                "arrayList=" + arrayList +
                ", Headline='" + Headline + '\'' +
                ", Headline_link='" + Headline_link + '\'' +
                '}';
    }

    public void setHeadline_link(String headline_link) {
        Headline_link = headline_link;
    }

    public ArrayList<DailyCast> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<DailyCast> arrayList) {
        this.arrayList = arrayList;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public static class DailyCast {

        String date;
        String mintemerature;
        String maxtemperature;
        String day_icon;
        String day_phrase;
        String night_icon;
        String night_phrase;

        String inner_mobilelink;

        @Override
        public String toString() {
            return "DailyCast{" +
                    "date='" + date + '\'' +
                    ", mintemerature='" + mintemerature + '\'' +
                    ", maxtemperature='" + maxtemperature + '\'' +
                    ", day_icon='" + day_icon + '\'' +
                    ", day_phrase='" + day_phrase + '\'' +
                    ", night_icon='" + night_icon + '\'' +
                    ", night_phrase='" + night_phrase + '\'' +
                    ", inner_mobilelink='" + inner_mobilelink + '\'' +
                    '}';
        }

        public String getInner_mobilelink() {
            return inner_mobilelink;
        }

        public void setInner_mobilelink(String inner_mobilelink) {
            this.inner_mobilelink = inner_mobilelink;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMaxtemperature() {
            return maxtemperature;
        }

        public void setMaxtemperature(String maxtemperature) {
            this.maxtemperature = maxtemperature;
        }

        public String getMintemerature() {
            return mintemerature;
        }

        public void setMintemerature(String mintemerature) {
            this.mintemerature = mintemerature;
        }

        public String getDay_icon() {
            return day_icon;
        }

        public void setDay_icon(String day_icon) {
            this.day_icon = day_icon;
        }

        public String getDay_phrase() {
            return day_phrase;
        }

        public void setDay_phrase(String day_phrase) {
            this.day_phrase = day_phrase;
        }

        public String getNight_icon() {
            return night_icon;
        }

        public void setNight_icon(String night_icon) {
            this.night_icon = night_icon;
        }

        public String getNight_phrase() {
            return night_phrase;
        }

        public void setNight_phrase(String night_phrase) {
            this.night_phrase = night_phrase;
        }
    }
}
