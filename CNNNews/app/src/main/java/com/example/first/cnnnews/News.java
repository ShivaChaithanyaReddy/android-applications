package com.example.first.cnnnews;

/**
 * Created by Chaithanya on 2/13/2017.
 */

public class News {

    String title;
    String desciption;
    String pubDate;
    String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", desciption='" + desciption + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
