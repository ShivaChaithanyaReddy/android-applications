package com.example.first.notekeeper;

/**
 * Created by Chaithanya on 2/27/2017.
 */

public class Note {

    private long _id;
    private String name, priority,time, status;

    public Note(String name, String priority,String time , String status) {

        this.name = name;
        this.priority = priority;
        this.time = time;
        this.status = status;
    }

    public Note() {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
