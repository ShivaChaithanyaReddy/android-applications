package com.example.first.notekeeper;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.INote{


    Spinner spinner;
    EditText name;
    NoteAdapter noteAdapter;
    Button button;
    DatabaseDataManager dm;
    List<Note> notes;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notes = new ArrayList<>();
        button = (Button) findViewById(R.id.button);
        spinner = (Spinner) findViewById(R.id.priority_array);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


//

        listView = (ListView) findViewById(R.id.listview);


        name = (EditText) findViewById(R.id.enterName);


        dm = new DatabaseDataManager(this);

        notes = dm.getAllNotes();

        noteAdapter = new NoteAdapter(this, R.layout.xmllayout,notes, MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String spinnerVal = spinner.getSelectedItem().toString();
                dm.saveNote(new Note(name.getText().toString(), spinnerVal,""+NANOSECONDS.toSeconds(System.nanoTime()/1000), "pending"));

                ////
               notes = dm.getAllNotes();

                noteAdapter = new NoteAdapter(MainActivity.this, R.layout.xmllayout,notes, MainActivity.this );
                listView.setAdapter(noteAdapter);
                //noteAdapter.notifyDataSetChanged();
                Log.d("demo", notes.toString());

                //
            }
        });

        listView.setAdapter(noteAdapter);
        noteAdapter.setNotifyOnChange(true);

    }

    @Override
    public void onCheckClick(int position) {

        final Note note = notes.get(position);

        long id = note.get_id();

        if(note.getStatus().equals("completed")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Remove From Favouries")
                    .setMessage("Are you sure you want to \n remove this app to favourites?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            note.setStatus("pending");
                            dm.updateNote(note);

                            Toast.makeText(MainActivity.this, "Successfully removed from Favourite list :)", Toast.LENGTH_SHORT).show();
                            notifyListUpdate();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
        else {
           note.setStatus("completed");
            dm.updateNote(note);
            notifyListUpdate();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.showall:
                showall();
               // refreshList();
                return true;
            case R.id.showc:
                showCompleted();
                //getFavourites();
                return true;
            case R.id.showp:
                showPending();
                //sortAsc();
                notifyListUpdate();
                return true;
            case R.id.sorttime:
                sortByTime();
                //sortDsc();
                notifyListUpdate();
                return true;
            case R.id.sortp:
                sortByPriority();
                //sortDsasc();
                notifyListUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByPriority() {

        {
            Collections.sort(notes, new Comparator<Note>() {
                @Override
                public int compare(Note m1, Note m2) {
                    String p1 = m1.getPriority();
                    String p2 = m2.getPriority();
                    if(p1 == null) return 1;
                    if(p2 == null) return -1;
                    if(p1.equals(p2)) return 0;
                    if(p1.equals("Low") && (p2.equals("Medium") || p2.equals("High")))
                        return -1;
                    if(p1.equals("Medium") && p2.equals("High"))
                        return -1;
                    return 1;
                }
            });

            Collections.reverse(notes);
        }
    }

    private void sortByTime() {
        Collections.sort(notes, new Comparator<Note>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Note c1, Note c2) {
                return Long.compare(Long.parseLong(c1.getTime()),Long.parseLong(c2.getTime()));
            }
        });
    }

    private void showPending() {
        List<Note> getAllN = dm.getAllNotes();
        List<Note> newList = new ArrayList<>();
        for(Note n: getAllN) {
            if(n.getStatus().equals("pending")) {
                newList.add(n);
            }
        }
        notes = newList;

        noteAdapter = new NoteAdapter(MainActivity.this, R.layout.xmllayout,notes, MainActivity.this );
        listView.setAdapter(noteAdapter);

    }

    private void showCompleted() {
        List<Note> getAllN = dm.getAllNotes();
        List<Note> newList = new ArrayList<>();
        for(Note n: getAllN) {
            if(n.getStatus().equals("completed")) {
                newList.add(n);
            }
        }
        notes = newList;
        noteAdapter = new NoteAdapter(MainActivity.this, R.layout.xmllayout,notes, MainActivity.this );
        listView.setAdapter(noteAdapter);

    }

    private void showall() {
        List<Note> getAllN = dm.getAllNotes();
        noteAdapter = new NoteAdapter(MainActivity.this, R.layout.xmllayout,getAllN, MainActivity.this );
        listView.setAdapter(noteAdapter);

        //noteAdapter.notifyDataSetChanged();
    }

    private void notifyListUpdate() {
        noteAdapter.notifyDataSetChanged();
    }
}
