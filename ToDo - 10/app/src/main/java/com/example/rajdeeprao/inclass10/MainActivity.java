package com.example.rajdeeprao.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomAdapter.IData {


    Button add;
    DatabaseReference myRef;
    FirebaseDatabase database;
    int ctr;
    ArrayList<Note> notesList;



    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        ctr=0;
        notesList=new ArrayList<>();

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //final ListView listView= (ListView) findViewById(R.id.listview);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(MainActivity.this, R.layout.my_list, notesList);

        mRecyclerView.setAdapter(mAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                notesList.clear();

                ctr=(int)dataSnapshot.child("notes").getChildrenCount();
                for (DataSnapshot postSnapshot: dataSnapshot.child("notes").getChildren()) {
                    Note note= postSnapshot.getValue(Note.class);
                    notesList.add(note);
                }
                mAdapter.notifyDataSetChanged();
                Log.d("NotesList",notesList.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });





        add= (Button) findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText= (EditText) findViewById(R.id.editText);
                String note=editText.getText().toString();
                String priority=spinner.getSelectedItem().toString();
                String time=new Date().toString();
                ctr++;
                Note noteObj=new Note(note,"Pending",priority,time);
                noteObj.set_id((long)ctr);
                myRef.child("notes").child(String.valueOf(ctr)).setValue(noteObj);

                //notesAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.MenuAll:

                return true;
            case R.id.MenuCompleted:

                return true;
            case R.id.MenuPending:

                return true;
            case R.id.MenuPriority:

                return true;
            case R.id.MenuTime:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void returnValues(int id) {
        myRef.child("notes").child(String.valueOf(id)).removeValue();
    }
}
