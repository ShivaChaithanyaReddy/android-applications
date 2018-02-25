package com.example.first.chitchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://52.90.79.130:8080/Groups/api";

    public static final String LOGIN_TOKEN = "loginToken";


    static SharedPreferences sharedPref ;
    Context context;
    final OkHttpClient client = new OkHttpClient();
    private final OkHttpClient client2 = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.context;
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.emails)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.passwords)).getText().toString().trim();
                if (null != email && null != password) {
                    try {
                        parseAPI("/login", email, password);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = ((EditText)findViewById(R.id.fname)).getText().toString().trim();
                String lName = ((EditText)findViewById(R.id.lname)).getText().toString().trim();
                String email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
                String pass = ((EditText)findViewById(R.id.password)).getText().toString().trim();
                if(null == email || null == fName || null == lName || null == pass){
                    Toast.makeText(MainActivity.this,"Error Signing Up. Please Try Again!",Toast.LENGTH_SHORT).show();
                }else{
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", email)
                            .add("fname", fName)
                            .add("lname", lName)
                            .add("password", pass)
                            .build();
                    try {
                        parseSignUp(formBody,"/signUp");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void parseAPI(String ext,String email,String password) throws IOException {
        Log.d("call API",ext);
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(URL+ext)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this,"Unexpected error",Toast.LENGTH_SHORT).show();
                Log.d("login","error");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("API response",res);
                Login login_main = ParseUtil.parseLogin(res);
                if(login_main.getStatus().equals("0")){
//                    Toast.makeText(MainActivity.this,login_main.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("login","Failed");

                }else {
                    //totalName = login_main.getUserFname()+" "+login_main.getUserLname();
                    Log.d("login","success");

                    setPreferences(login_main.getToken());
                    Intent intent = new Intent(MainActivity.this,SubscribedChannel.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                }
            }
        });
    }

    private void parseSignUp(RequestBody formBody,String ext) throws IOException {
        Log.d("call API",ext);

        Request request = new Request.Builder()
                .url(MainActivity.URL+ext)
                .post(formBody)
                .build();
        client2.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this,"Unexpected error",Toast.LENGTH_SHORT).show();
                Log.d("Signup","unexpected error");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("API response",res);
                Signup signUp = ParseUtil.parseSignUp(res);
                if(signUp.getStatus().equals(0)){
                 //   Toast.makeText(MainActivity.this,signUp.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Signup","Failed");
                }else{
                    setPreferences(signUp.getToken());
//                    Toast.makeText(MainActivity.this,"Sign Up Successful!",Toast.LENGTH_SHORT).show();
                    Log.d("Signup","Success"+ signUp.getToken());

                }
            }
        });
    }

    static void setPreferences(String token){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGIN_TOKEN, token);
        editor.commit();
    }
}
