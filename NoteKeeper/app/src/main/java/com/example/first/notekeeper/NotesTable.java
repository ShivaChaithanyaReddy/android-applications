package com.example.first.notekeeper;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Chaithanya on 2/27/2017.
 */

public class NotesTable {

    static final String TABLENAME = "notes";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME= "name";
    static final String COLUMN_STATUS ="status";
    static final String COLUMN_PRIORITY ="priority";
    static final String COLUMN_TIME ="time";

    static public void onCreate(SQLiteDatabase db){
        Log.d("asdfasdf","In NotesTable");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE "+ TABLENAME + "(" );
        stringBuilder.append(COLUMN_ID+" integer primary key autoincrement, ");
        stringBuilder.append(COLUMN_NAME+" text not null, ");
        stringBuilder.append(COLUMN_STATUS+" text not null, ");
        stringBuilder.append(COLUMN_PRIORITY+" text not null, ");
        stringBuilder.append(COLUMN_TIME+ " text not null); ");

        try {
            db.execSQL(stringBuilder.toString());
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    static public void onUpgarde(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLENAME);
        NotesTable.onCreate(db);
    }
}
