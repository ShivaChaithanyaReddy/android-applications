package com.example.first.tedpodcast;

import java.io.Serializable;

/**
 * Created by vandita on 3/9/17.
 */

public class Podcast implements Serializable, Comparable<Podcast>{

    String title, releaseDate, image, link, duration, description, Ctimedate;

    public String getCtimedate() {
        return Ctimedate;
    }

    public void setCtimedate(String ctimedate) {
        Ctimedate = ctimedate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", duration='" + duration + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Podcast o) {
        if(getDuration().split("&&")[1] == null || o.getDuration().split("&&")[1] == null)
            return 0;
        return getDuration().split("&&")[1].compareTo(o.getDuration().split("&&")[1]);
    }
}
