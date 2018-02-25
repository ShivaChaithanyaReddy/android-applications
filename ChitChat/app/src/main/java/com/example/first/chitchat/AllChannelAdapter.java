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

import static com.example.first.chitchat.SubscribedChannel.listChannels;


public class AllChannelAdapter extends ArrayAdapter<AllChannelGetSet.ChannelBean> {

    Context context;
    int resource;

    int flag =0;

    public final static String POS = "position";


    List<AllChannelGetSet.ChannelBean> channelList ;
    public AllChannelAdapter(Context context, int resource, List<AllChannelGetSet.ChannelBean> channelList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }

        final AllChannelGetSet.ChannelBean channel = channelList.get(position);
        //Log.d("outsersre",""+(String)msg.getFileThumbnailId());
        TextView txt = (TextView)convertView.findViewById(R.id.textView);


        for(Channels.ChannelBean n: listChannels) {
            if(channel.getChannel_name() .equals(n.getChannel().getChannel_name())) {
                flag = 1;
            }
        }

        Button btn = (Button) convertView.findViewById(R.id.button);


        if(flag == 1) {
            flag =0;
            btn.setText("View");

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),DisplaySubscription.class);
                    Log.d("ppppppppp",""+channel.getChannel_id());
                    intent.putExtra(POS,position);
                    context.startActivity(intent);
                }
            });

        }else {
            btn.setText("Join");
        }


        txt.setText(channel.getChannel_name());

        return convertView;
    }
}
