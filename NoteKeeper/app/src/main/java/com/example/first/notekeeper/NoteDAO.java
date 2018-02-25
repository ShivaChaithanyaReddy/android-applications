package com.example.first.notekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaithanya on 2/27/2017.
 */

public class NoteDAO {
    private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note) {

        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NAME, note.getName());
        values.put(NotesTable.COLUMN_STATUS, note.getStatus());
        values.put(NotesTable.COLUMN_PRIORITY, note.getPriority());
        values.put(NotesTable.COLUMN_TIME, note.getTime());
        return db.insert(NotesTable.TABLENAME, null, values);
    }

    public boolean update(Note note) {

        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NAME, note.getName());
        values.put(NotesTable.COLUMN_STATUS, note.getStatus());
        values.put(NotesTable.COLUMN_PRIORITY, note.getPriority());
        values.put(NotesTable.COLUMN_TIME, note.getTime());
        return db.update(NotesTable.TABLENAME, values, NotesTable.COLUMN_ID+"=?", new String[]{note.get_id()+""}) > 0;
    }
    public boolean delete(Note note) {
        Log.d("In delete"," "+ note.get_id());
        return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_ID + "=?", new String[]{note.get_id()+""}) > 0;

    }
    public Note get(long id) {

        Note note = null;
        Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_NAME, NotesTable.COLUMN_STATUS,NotesTable.COLUMN_PRIORITY, NotesTable.COLUMN_TIME}
                ,NotesTable.COLUMN_ID+ "=?", new String[]{id+""}, null, null, null, null, null);
/////////////////to remove the error chnage version to 16 in manifest

        if(c != null && c.moveToFirst()) {
            note = buildNoteFromCursor(c);
            if(!c.isClosed()) {
                c.close();
            }

        }
        return  note;
    }

    public List<Note> getAll() {
        List<Note> notes = new ArrayList<Note>();

        Cursor c = db.query(NotesTable.TABLENAME, new String[] {
                NotesTable.COLUMN_ID,NotesTable.COLUMN_NAME,
                NotesTable.COLUMN_STATUS, NotesTable.COLUMN_PRIORITY,
                NotesTable.COLUMN_TIME}, null, null, null, null, null);

        if(c != null && c.moveToFirst()) {

            do{
                Note note = buildNoteFromCursor(c);
                if(note != null) {
                    notes.add(note);
                }
            }while (c.moveToNext());
            if(!c.isClosed()) {
                c.close();
            }
        }
        return  notes;
    }

    private Note buildNoteFromCursor(Cursor c) {
        Note note = null;
        if(c != null) {
            note = new Note();
            note.set_id(c.getLong(0));
            note.setName(c.getString(1));
            note.setStatus(c.getString(2));
            note.setPriority(c.getString(3));
            note.setTime(c.getString(4));
        }
        return note;
    }

}
