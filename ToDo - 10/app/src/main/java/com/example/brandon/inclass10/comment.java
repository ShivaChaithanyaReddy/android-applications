package com.example.brandon.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class comment extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        String name = getIntent().getStringExtra(RecViewAdapter.NAME);
        final String id = getIntent().getStringExtra(RecViewAdapter.MSGID);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");


        findViewById(R.id.commentBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText) findViewById(R.id.comments);
                String comm = e.getText().toString();

                String random =  ""+UUID.randomUUID();
                myRef.child("Messages").child(id).child("comments").child(random).setValue(comm);

            }
        });



    }
}
