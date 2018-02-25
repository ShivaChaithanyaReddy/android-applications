package com.example.first.chitchat;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChannelAdapter extends ArrayAdapter<Channels.ChannelBean> {


    public final static String POS1 = "position1";



    Context context;
    int resource;
    List<Channels.ChannelBean> channelList ;
    public ChannelAdapter(Context context, int resource, List<Channels.ChannelBean> channelList) {
        super(context, resource, channelList);
        this.context = context;
        this.resource = resource;
        this.channelList = channelList;
        Log.d("consxss",channelList.size()+"");
    }

    @Override
    public int getCount() {
        Log.d("messages count",""+channelList.size());
        return channelList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }

        final Channels.ChannelBean channel = channelList.get(position);
        //Log.d("outsersre",""+(String)msg.getFileThumbnailId());
        TextView txt = (TextView)convertView.findViewById(R.id.textView);
        Button btn = (Button) convertView.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DisplaySubscription.class);

                intent.putExtra(POS1,channel.getChannel().getChannel_id());
                context.startActivity(intent);
            }
        });

        txt.setText(channel.getChannel().getChannel_name());

        return convertView;
    }
}
