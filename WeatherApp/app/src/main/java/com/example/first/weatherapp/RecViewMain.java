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

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Chaithanya on 3/11/2017.
 */

public class RecViewMain extends  RecyclerView.Adapter<RecViewMain.RecViewHolder>{



    private ArrayList<SaveInFirebase> arrayList;
    private LayoutInflater layoutInflater;
    public Context context;
    public static int flag=0;
    public static String  OBJECT = "data";
    public static String BASEURL = "url";

    private JSONParsing.IActivity iActivity;

    public RecViewMain( ArrayList<SaveInFirebase> arrayList, Context context, JSONParsing.IActivity iActivity) {
        this.arrayList = arrayList;
        this.context=context;
        this.iActivity = iActivity;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rec_view, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, final int position) {

        final SaveInFirebase dailyCast = arrayList.get(position);

        Log.d("Dailycat is : ",""+dailyCast.toString());

        PrettyTime p = new PrettyTime();


        holder.temperature_recview_text.setText("Temperature: "+dailyCast.getTempearture());
        holder.last_updated_text.setText("Last Updated: "+p.format(new Date(dailyCast.getTime())));
        holder.cityname_recview_text.setText(dailyCast.getCity()+", "+dailyCast.getCountry());

        if(dailyCast.isFavorite()) {
            Picasso.with(context).load(R.drawable.stargold).into(holder.imageView);

        }else
            Picasso.with(context).load(R.drawable.stargray).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iActivity.onClickStar(position);
            }
        });

        holder.lview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                iActivity.OnLongClickDelete(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {


        private TextView cityname_recview_text;
        private TextView temperature_recview_text;
        private TextView last_updated_text;
        private ImageView imageView;

        private LinearLayout lview;

        public RecViewHolder(View itemView) {
            super(itemView);

            temperature_recview_text = (TextView) itemView.findViewById(R.id.temperature_recview_text);
            cityname_recview_text = (TextView) itemView.findViewById(R.id.cityname_recview_text);
            last_updated_text = (TextView) itemView.findViewById(R.id.last_updated_text);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            lview = (LinearLayout) itemView.findViewById(R.id.main_outer_layout);
        }
    }
}