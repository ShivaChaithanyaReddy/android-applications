package com.example.first.tedpodcast;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chaithanya on 3/11/2017.
 */

public class RecViewAdapter extends  RecyclerView.Adapter<RecViewAdapter.RecViewHolder>{



    private ArrayList<Podcast> arrayList;
    private LayoutInflater layoutInflater;
    public Context context;
    static MediaPlayer mediaPlayer;
    public static int flag=0;
    public static String  OBJECT = "data";

    public RecViewAdapter(ArrayList<Podcast> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, int position) {
        final Podcast podcast = arrayList.get(position);
        holder.mtitle.setText(podcast.getTitle());
        holder.mdate.setText("Posted: "+podcast.getReleaseDate().split("&&")[0]);
        Picasso.with(context).load(podcast.getImage()).into(holder.mimageView);


        holder.mplaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag ==0) {
                flag =1;
                    mediaPlayer = new MediaPlayer();
                    CustomMediaPlayer player = new CustomMediaPlayer();
                    player.setSongUrlObject(podcast, mediaPlayer,0);
                }
                else {
                    mediaPlayer.reset();
                    CustomMediaPlayer player = new CustomMediaPlayer();
                    player.setSongUrlObject(podcast, mediaPlayer,0);
                }
                MainActivity.play_pause.setVisibility(View.VISIBLE);
                MainActivity.progressBar.setVisibility(View.VISIBLE);

            }
        });

        holder.Item_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra(OBJECT, podcast);
                context.startActivity(intent);
                MainActivity.play_pause.setVisibility(View.GONE);
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
      //  holder.mplaynow.setText(podcast.getLink());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder {

        private ImageView mimageView;
        private TextView mtitle;
        private TextView mdate;
        private ImageView mplaynow;
        private View container_item_root;
        private LinearLayout Item_View;


        private ImageView play_pause;


        public RecViewHolder(View itemView) {
            super(itemView);

            mimageView  =(ImageView) itemView.findViewById(R.id.imageView);
            mtitle = (TextView) itemView.findViewById(R.id.title);
            mdate = (TextView) itemView.findViewById(R.id.date);
            mplaynow = (ImageView) itemView.findViewById(R.id.imageView2);

            play_pause = (ImageView) itemView.findViewById(R.id.pause_play1);

            Item_View = (LinearLayout) itemView.findViewById(R.id.each_item);

        }
    }
}