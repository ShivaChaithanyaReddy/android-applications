package com.example.brandon.inclass10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;

    EditText email;
    EditText pwd;

    Button login;

    Button signup;

    public static String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.txtEmail);
        pwd = (EditText) findViewById(R.id.txtPassword);

        login = (Button) findViewById(R.id.btnLogin);

        signup = (Button) findViewById(R.id.btnCreateUser);



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String em = email.getText().toString();
                final String pw = pwd.getText().toString();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Log.d("TAG", "Value is: " + postSnapshot);

                            if(em.equals(postSnapshot.getKey() ) && pw.equals(postSnapshot.getValue(Signup.class).getPwd1()) ) {
                                Log.d("TAG", "Value is: " + postSnapshot.getValue());

                                Intent intent = new Intent(MainActivity.this, ChatRoom.class);
                                intent.putExtra(USER,postSnapshot.getKey());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Please enter correct details", Toast.LENGTH_SHORT).show();
                            }
                        }


                        if(!dataSnapshot.hasChildren()) {
                            Toast.makeText(MainActivity.this, "No user",Toast.LENGTH_SHORT).show();
                        }else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");



    }

}
