package com.example.rajdeeprao.inclass10;

import java.util.Comparator;

public class CustomComparator implements Comparator<Note> {
    @Override
    public int compare(Note o1, Note o2) {
        if(o1.getStatus().equals("COMPLETED") && o2.getStatus().equals("COMPLETED")  )
        {
            return checkPriority(o1, o2);
        }else if(o1.getStatus().equals("COMPLETED")) {
            return 1;
        }
        else if(o2.getStatus().equals("COMPLETED")) {
            return -1;
        }else{
            return checkPriority(o1, o2);
        }
    }

    private int checkPriority(Note o1, Note o2) {
        if(o1.getPriority().equals("High")) {
            if(o2.getPriority().equals("High")) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else if(o1.getPriority().equals("Medium")){
            if(o2.getPriority().equals("High")) {
                return 1;
            }
            else if(o2.getPriority().equals("Medium")){
                return 0;
            }else{
                return -1;
            }
        }else{
            if(o2.getPriority().equals("Low")) {
                return 0;
            }
            else {
                return -1;
            }
        }
    }
}
