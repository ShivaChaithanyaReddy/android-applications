package com.example.first.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Chaithanya on 2/27/2017.
 */

public class DatabaseDataManager {

    private Context mcontext;
    private  DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context mcontext) {
        this.mcontext = mcontext;
        this.dbOpenHelper = new DatabaseOpenHelper(this.mcontext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
    }

    public  void close() {
        if(db!= null) {
            db.close();
        }
    }

    public NoteDAO getNoteDAO() {
        return this.noteDAO;
    }

    public long saveNote(Note note) {
        return this.noteDAO.save(note);
    }

    public boolean updateNote(Note note) {
        return this.noteDAO.update(note);
    }

    public boolean deleteNote(Note note) {
        return this.noteDAO.delete(note);
    }

    public Note getNote(long id) {
        return this.noteDAO.get(id);
    }

    public List<Note> getAllNotes() {
        return  this.noteDAO.getAll();
    }
}
