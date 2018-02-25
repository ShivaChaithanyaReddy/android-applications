package com.example.rajdeeprao.inclass10;

/**
 * Created by rajdeeprao on 2/26/17.
 */

public class Note {
    String note,status,priority;
    long _id;

    @Override
    public String toString() {
        return "Note{" +
                "note='" + note + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", _id=" + _id +
                ", update_time=" + update_time +
                '}';
    }

    public Note() {
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Note(String note, String status, String priority, String update_time) {
        this.note = note;
        this.status = status;
        this.priority = priority;
        this.update_time = update_time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    String update_time;
}
