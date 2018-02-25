package com.example.first.tedpodcast;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.example.first.tedpodcast.R.id.imageView;
import static com.example.first.tedpodcast.R.id.list_item;

/**
 * Created by Chaithanya on 3/11/2017.
 */

public class GridViewAdapter extends  RecyclerView.Adapter<GridViewAdapter.RecViewHolder>{


    private ArrayList<Podcast> arrayList;
    private LayoutInflater layoutInflater;
    public Context context;
    int flag =0;
    public static String  OBJECT = "data";


    public GridViewAdapter(ArrayList<Podcast> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.gridlayout, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        final Podcast podcast = arrayList.get(position);
        Picasso.with(context).load(podcast.getImage()).into(holder.mimageView);
        holder.mtitle.setText(podcast.getTitle());
        holder.mplaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((RecViewAdapter.flag ==0)) {
                    RecViewAdapter.flag =1;
                    RecViewAdapter.mediaPlayer = new MediaPlayer();
                    CustomMediaPlayer player = new CustomMediaPlayer();
                    player.setSongUrlObject(podcast, RecViewAdapter.mediaPlayer,0);
                }
                else {
                    RecViewAdapter.mediaPlayer.reset();
                    CustomMediaPlayer player = new CustomMediaPlayer();
                    player.setSongUrlObject(podcast, RecViewAdapter.mediaPlayer,0);
                }
                MainActivity.play_pause.setVisibility(View.VISIBLE);
                MainActivity.progressBar.setVisibility(View.VISIBLE);

            }
        });

        holder.CompleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra(OBJECT, podcast);
                context.startActivity(intent);
                MainActivity.play_pause.setVisibility(View.GONE);
                MainActivity.progressBar.setVisibility(View.GONE);
            }
        });
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
        private FrameLayout CompleteLayout;


        public RecViewHolder(View itemView) {
            super(itemView);

            mimageView  =(ImageView) itemView.findViewById(R.id.imageView_inGrid);
            mtitle = (TextView) itemView.findViewById(R.id.title_inGrid);
            mplaynow = (ImageView) itemView.findViewById(R.id.imageView3);

            CompleteLayout = (FrameLayout) itemView.findViewById(R.id.complete_layout);
        }
    }
}