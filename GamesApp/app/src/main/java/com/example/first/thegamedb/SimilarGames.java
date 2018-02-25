package com.example.first.thegamedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by Chaithanya on 2/17/2017.
 */

public class SimilarGames implements Serializable{

    String id;
    String platform;


    @Override
    public String toString() {
        return "SimilarGames{" +
                "id='" + id + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
