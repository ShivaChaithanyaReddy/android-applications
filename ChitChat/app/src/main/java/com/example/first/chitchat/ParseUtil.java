package com.example.first.chitchat;

import com.google.gson.Gson;

/**
 * Created by Chaithanya on 3/27/2017.
 */


public class ParseUtil {
    static Login parseLogin(String in){
        Login login = new Gson().fromJson(in,Login.class);
        return login;
    }
    static Signup parseSignUp(String in){
        Signup signUp = new Gson().fromJson(in,Signup.class);
        return signUp;
    }
    static Message parseMessageResponse(String in){
        return new Gson().fromJson(in,Message.class);
    }
    static Channels parseChannelResponse(String in){
        return new Gson().fromJson(in,Channels.class);
    }

    static AllChannelGetSet parseAllChannelResponse(String in){
        return new Gson().fromJson(in,AllChannelGetSet.class);
    }

}
