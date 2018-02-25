package com.example.first.chitchat;

import android.content.SharedPreferences;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AllChannel extends AppCompatActivity {

    private final OkHttpClient chatClient = new OkHttpClient();
    List<AllChannelGetSet.ChannelBean> listChannels;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_channel);
        listChannels =  new ArrayList<>();

        listView = (ListView)findViewById(R.id.listViewitem);

        try {
            //  String name =     //  getIntent().getExtras().getString(SignUP.FULL_NAME);
            // Log.d("name retrieved is: ",""+name);
            callAllAPI("/get/subscriptions");
            Log.d("after on response",""+listChannels.size());

        } catch (IOException e) {
            e.printStackTrace();
        }



        findViewById(R.id.addbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Channels.ChannelBean> allChannels =new ArrayList<Channels.ChannelBean>();

                    //  String name =     //  getIntent().getExtras().getString(SignUP.FULL_NAME);
                    // Log.d("name retrieved is: ",""+name);
                  finish();
            }
        });
    }



    private void callAllAPI(String ext) throws IOException {
        String header =  MainActivity.sharedPref.getString(MainActivity.LOGIN_TOKEN,null);
        Log.d("call API",ext+" :: "+header);

        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api"+"/get/channels")
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

                AllChannelGetSet channels = ParseUtil.parseAllChannelResponse(res);

                if(channels.getStatus().equals(0)){
                    Toast.makeText(AllChannel.this,"Error",Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("asdsadsadsadas","---------");
                    listChannels = channels.getData();
                    Log.d("iside on response",""+listChannels.get(0).getChannel_id());
                    AllChannelAdapter channelAdapter5 = new AllChannelAdapter(AllChannel.this,R.layout.row_item_layout,listChannels);
                    channelAdapter5.setNotifyOnChange(true);
                    ListView listView1 = (ListView)findViewById(R.id.listViewitem);

                    listView1.setAdapter(channelAdapter5);
                }
            }
        });
    }




}
