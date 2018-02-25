package com.example.first.weatherapp;



import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Chaithanya on 3/11/2017.
 */

public class RecViewAdapter extends  RecyclerView.Adapter<RecViewAdapter.RecViewHolder>{



    private ArrayList<FiveDay.DailyCast> arrayList;
    private LayoutInflater layoutInflater;
    public Context context;
    public static int flag=0;
    public static String  OBJECT = "data";
    public static String BASEURL = "url";

    private JSONParsing.IActivity iActivity;

    public RecViewAdapter( ArrayList<FiveDay.DailyCast> arrayList, Context context, JSONParsing.IActivity iActivity) {
        this.arrayList = arrayList;
        this.context=context;
        this.iActivity = iActivity;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rec_view_layout, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, final int position) {

        final FiveDay.DailyCast dailyCast = arrayList.get(position);


        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
        Date date = null;
        try {
            date = format.parse(dailyCast.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(" MMM''yy");


        if(Integer.parseInt(new SimpleDateFormat("dd").format(date)) > 3)
        holder.fortitle.setText(Integer.parseInt(new SimpleDateFormat("dd").format(date))+"th"+formatter.format(date));
        else if(Integer.parseInt(new SimpleDateFormat("dd").format(date)) ==1)
            holder.fortitle.setText(Integer.parseInt(new SimpleDateFormat("dd").format(date))+"st"+formatter.format(date));
            else if(Integer.parseInt(new SimpleDateFormat("dd").format(date)) == 2)
            holder.fortitle.setText(Integer.parseInt(new SimpleDateFormat("dd").format(date))+"nd"+formatter.format(date));
            else if(Integer.parseInt(new SimpleDateFormat("dd").format(date)) == 3)
            holder.fortitle.setText(Integer.parseInt(new SimpleDateFormat("dd").format(date))+"rd"+formatter.format(date));

        if(Integer.parseInt(dailyCast.getDay_icon()) <10) {
            Picasso.with(context).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(dailyCast.getDay_icon())+"-s.png").into(holder.forimagelogo);

        }else
        Picasso.with(context).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(dailyCast.getDay_icon())+"-s.png").into(holder.forimagelogo);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iActivity.showDayAndNight(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {

        private ImageView forimagelogo;
        private TextView fortitle;
        private LinearLayout Item_View;

        public RecViewHolder(View itemView) {
            super(itemView);

            forimagelogo  =(ImageView) itemView.findViewById(R.id.imageView2);
            fortitle = (TextView) itemView.findViewById(R.id.textView);
            Item_View = (LinearLayout) itemView.findViewById(R.id.Complete_layout);

        }
    }
}