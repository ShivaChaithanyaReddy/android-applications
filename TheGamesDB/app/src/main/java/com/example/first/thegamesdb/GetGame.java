package com.example.first.thegamesdb;

import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/17/2017.
 */

public class GetGame {

    String baseurl;
    String title;
    String image;
    String overview;
    String genre;
    String publisher;
    String trailer;
    String releaseDate;
    String platform;
    ArrayList<SimilarGames> arrayList;
    String SimilarCount;

    @Override
    public String toString() {
        return "GetGame{" +
                "baseurl='" + baseurl + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", overview='" + overview + '\'' +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", trailer='" + trailer + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platform='" + platform + '\'' +
                ", arrayList=" + arrayList +
                '}';
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public ArrayList<SimilarGames> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<SimilarGames> arrayList) {
        this.arrayList = arrayList;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}


