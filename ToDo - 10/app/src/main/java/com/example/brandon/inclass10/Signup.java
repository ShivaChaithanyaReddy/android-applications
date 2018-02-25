package com.example.brandon.inclass10;

import java.io.Serializable;

/**
 * Created by Chaithanya on 4/10/2017.
 */

public class Signup implements Serializable{

    String fname;
    String lname;
    String email;
    String pwd1;
    String pwd2;

    public Signup() {}

    public Signup(String email, String fname, String lname, String pwd1, String pwd2) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.pwd1 = pwd1;
        this.pwd2 = pwd2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPwd1() {
        return pwd1;
    }

    public void setPwd1(String pwd1) {
        this.pwd1 = pwd1;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }
}
