package com.example.brandon.inclass10;



import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.brandon.inclass10.ChatRoom.arrayList;

/**
 * Created by Chaithanya on 3/11/2017.
 */

public class RecViewAdapter extends  RecyclerView.Adapter<RecViewAdapter.RecViewHolder>{


    StorageReference storageRef ;

    private LayoutInflater layoutInflater;
    public Context context;
    public static int flag=0;
    public static String  OBJECT = "data";
    public static String BASEURL = "url";


    public static String  NAME = "name";
    public static String MSGID = "id";

    private IActivity iActivity;

    public RecViewAdapter(ArrayList<Messages> arrayList, Context context, IActivity iActivity) {
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


        FirebaseApp.initializeApp(context);




        final Messages dailyCast = arrayList.get(position);


        if(!dailyCast.getImage().equals("empty") && !dailyCast.getImage().isEmpty() ) {
            storageRef = FirebaseStorage.getInstance().getReference().child(dailyCast.getImage());




            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(storageRef)
                    .into(holder.photo);

            holder.photo.setVisibility(View.VISIBLE);


        }
/*
        ArrayList<String> aList = dailyCast.getComments();

        for(String a: aList) {

        }
*/


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        PrettyTime p =new PrettyTime();

        holder.name.setText(dailyCast.getName());
        holder.message.setText(dailyCast.getMsg());
        try {
            holder.time.setText(p.format(dateFormat.parse(dailyCast.getTime())).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
  /*


        if(Integer.parseInt(dailyCast.getDay_icon()) <10) {
            Picasso.with(context).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(dailyCast.getDay_icon())+"-s.png").into(holder.forimagelogo);

        }else
        Picasso.with(context).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(dailyCast.getDay_icon())+"-s.png").into(holder.forimagelogo);
*/


        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,comment.class);
                intent.putExtra(NAME, dailyCast.getName());
                intent.putExtra(MSGID, dailyCast.getKey());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   iActivity.showDayAndNight(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {

        private ImageView trash;
        private  ImageView comment;
        private TextView message;
        private TextView name;
        private  TextView time;

        private ImageView photo;

        //comments
        private TextView cname;



        private LinearLayout Item_View;

        public RecViewHolder(View itemView) {
            super(itemView);


            trash = (ImageView)  itemView.findViewById(R.id.imageView3);
            comment = (ImageView) itemView.findViewById(R.id.imageView2);
            name = (TextView) itemView.findViewById(R.id.textView);
            message = (TextView) itemView.findViewById(R.id.textView2);
            time = (TextView) itemView.findViewById(R.id.time);

            photo = (ImageView) itemView.findViewById(R.id.imageView4);
        }
    }

    public interface IActivity {
        void senddata(int pos);

    }
}