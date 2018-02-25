package com.example.brandon.inclass10;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Brandon on 4/3/2017.
 */

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText fname;
    EditText lname;
    EditText email;
    EditText password1;
    EditText password2;



    FirebaseDatabase database;
    DatabaseReference myRef;



    public static final String TAG = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");



        fname = (EditText) findViewById(R.id.editText3);
        lname = (EditText) findViewById(R.id.editText4);
        email = (EditText) findViewById(R.id.editText5);
        password1 = (EditText) findViewById(R.id.editText8);
        password2 = (EditText) findViewById(R.id.editText9);



        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname1 = fname.getText().toString();
                String lname1 = lname.getText().toString();
                String email1 = email.getText().toString();
                String pwd1 = password1.getText().toString();
                String pwd2 = password2.getText().toString();

if(fname1!=null && lname1!=null && email1!=null && pwd1!=null &pwd2!=null && (pwd1==pwd2)){
    Signup signup = new Signup(email1, fname1,lname1,pwd1,pwd2);

    myRef.child(email1).setValue(signup);

}
                else
{
    Toast.makeText(SecondActivity.this,"Please enter correct details", Toast.LENGTH_SHORT).show();

}


                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot postSnapshot: dataSnapshot.child("Cities").getChildren()) {
                            Log.d("TAG", "Value is: " + postSnapshot);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


            }
        });




    }
}
