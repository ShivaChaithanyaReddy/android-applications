package com.example.rajdeeprao.inclass10;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by rajdeeprao on 2/27/17.
 */

public class TimeComparator implements Comparator<Note> {

    @Override
    public int compare(Note o1, Note o2) {
        if(o1.getStatus().equals("COMPLETED") && o2.getStatus().equals("COMPLETED")  )
        {
            return checkTime(o1, o2);
        }else if(o1.getStatus().equals("COMPLETED")) {
            return 1;
        }
        else if(o2.getStatus().equals("COMPLETED")) {
            return -1;
        }else{
            return checkTime(o1, o2);
        }
    }

    private int checkTime(Note o1, Note o2) {

        Date date1,date2;
        date1=new Date(o1.getUpdate_time());
        date2=new Date(o2.getUpdate_time());

        if(date1.after(date2))
            return 1;
        else if(date2.after(date1))
            return -1;
        else return 0;

    }
}
