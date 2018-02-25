package com.example.brandon.inclass10;

import android.os.Message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 4/10/2017.
 */

public class Messages implements Serializable {

    String name;
    String msg;
    String time;

    String key;
    String image;


    ArrayList<String> comments;


    public Messages(){}

    public Messages(String image, String msg, String name, String time, String key,ArrayList<String> comments) {
        this.image = image;
        this.msg = msg;
        this.name = name;
        this.time = time;
        this.key = key;
        this.comments = comments;
    }


    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
