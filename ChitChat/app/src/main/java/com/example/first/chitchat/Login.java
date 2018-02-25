package com.example.first.chitchat;

/**
 * Created by vandita on 3/27/17.
 */

public class Login {

    private String status;
    private String data;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return data;
    }

    public void setToken(String token) {
        this.data = token;
    }


}
