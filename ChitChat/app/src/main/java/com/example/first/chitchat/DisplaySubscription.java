package com.example.first.chitchat;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisplaySubscription extends AppCompatActivity {

    private final OkHttpClient chatClient = new OkHttpClient();

    int pos =0;
    List<Message.MessageBeam> Allmessages;
    LinearLayout ll ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_subscription);


Allmessages = new ArrayList<>();
ll = (LinearLayout) findViewById(R.id.ll);

         pos = getIntent().getIntExtra("position",0);


        try {
            //  String name =     //  getIntent().getExtras().getString(SignUP.FULL_NAME);
            // Log.d("name retrieved is: ",""+name);


            callAPI("/get/messages?channel_id="+pos);

            Log.d("position is",""+pos);

        } catch (IOException e) {
            e.printStackTrace();
        }



        findViewById(R.id.button2send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed = (EditText) findViewById(R.id.editText);

                String msg = ed.getText().toString().trim();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String dtime = dateFormat.format(date);    //2016/11/16 12:08:43


                PrettyTime p  = new PrettyTime();
                String datetime= p.format(new Date(dtime));


                RequestBody formBody = new FormBody.Builder()
                        .add("msg_text",msg )
                        .add("msg_time", datetime)
                        .add("channel_id",String.valueOf(pos) )
                        .build();

                try {
                    parseSignUp(formBody,"/post/message");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(DisplaySubscription.this, newClass.class);
                intent.putExtra("position",pos);
                startActivity(intent);
                finish();
//                    callAPI("/get/messages?channel_id="+pos);

            }
        });


    }


    private void parseSignUp(RequestBody formBody,String ext) throws IOException {
        Log.d("call API",ext);

        Request request = new Request.Builder()
                .url(MainActivity.URL+ext)
                .post(formBody)
                .build();
        chatClient.newCall(request).enqueue(new Callback() {
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
//                    Toast.makeText(MainActivity.this,"Sign Up Successful!",Toast.LENGTH_SHORT).show();
                    Log.d("Signup","Success"+ signUp.getToken());

                }
            }
        });
    }


    private void callAPI(String ext) throws IOException {
        String header =  MainActivity.sharedPref.getString(MainActivity.LOGIN_TOKEN,null);
        Log.d("call API",ext+" :: "+header);

        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api"+ext)
                .addHeader("Authorization"/*MainActivity.AUTHORIZATION*/,"BEARER "+header)
                .build();
        chatClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(SubscribedChannel.this,"Unexpected error",Toast.LENGTH_SHORT).show();
                Log.d("asdasdas","failed");
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("API response",res);

                Message channels = ParseUtil.parseMessageResponse(res);
                Log.d("Parse",channels.getData().toString());

                if(channels.getStatus().equals(0)){
                    Toast.makeText(DisplaySubscription.this,"Error",Toast.LENGTH_SHORT).show();
                }else {
                    Allmessages = channels.getData();
                    Log.d("iside on response",""+Allmessages.size());

                    for(Message.MessageBeam m:Allmessages) {
                        TextView t = new TextView(DisplaySubscription.this);
//                        t.setText(m.getObj().getFname()+" "+m.getObj().getLname());

                        TextView t1 = new TextView(DisplaySubscription.this);
                        t1.setText(m.getMessages_text());

                        TextView t2 = new TextView(DisplaySubscription.this);
                        t2.setText(m.getMsg_time());

                        TextView t3 = new TextView(DisplaySubscription.this);
                        t3.setText("--------------------------------------------------");
                        ll.addView(t);
                        ll.addView(t1);
                        ll.addView(t2);
                        ll.addView(t3);
                    }

                }
            }
        });
    }
}
