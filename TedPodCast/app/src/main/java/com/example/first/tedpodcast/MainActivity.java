package com.example.first.tedpodcast;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements getPodcastAsync.IPodcast {

    private  RecyclerView recyclerView;
    private GridViewAdapter adapter;
    private RecViewAdapter recViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    ImageView img;
    ArrayList<Podcast> arrayList = new ArrayList<>();

    static ImageView play_pause;
    static SeekBar progressBar;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.logo);

        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
*/

        img = (ImageView) findViewById(R.id.imageView4);

        play_pause = (ImageView) findViewById(R.id.pause_play1);
        progressBar = (SeekBar) findViewById(R.id.progressBar1);

        play_pause.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        progressDialog.setCancelable(false);


        new getPodcastAsync(this).execute("https://www.npr.org/rss/podcast.php?id=510298");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mycustommenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_shuffle:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                if(flag == false) {
                    flag = true;
                    GridLayoutManager mgridLayoutManager = new GridLayoutManager(MainActivity.this,2);
                    recyclerView.setLayoutManager(mgridLayoutManager);
                    // specify an adapter (see also next example)
                    adapter = new GridViewAdapter(arrayList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }else {
                    flag = false;
                    mLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(mLayoutManager);

                    // specify an adapter (see also next example)
                    recViewAdapter = new RecViewAdapter(arrayList, MainActivity.this);
                    recyclerView.setAdapter(recViewAdapter);
                }

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(RecViewAdapter.flag ==1)
            RecViewAdapter.mediaPlayer.reset();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(RecViewAdapter.flag ==1)
            RecViewAdapter.mediaPlayer.reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(RecViewAdapter.flag ==1)
        RecViewAdapter.mediaPlayer.reset();
    }

    @Override
    public void ParsedData(final ArrayList<Podcast> podcasts) {

        arrayList = podcasts;
      //  Collections.sort(podcasts);
        progressDialog.dismiss();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

/*
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
*/

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

          // specify an adapter (see also next example)
        recViewAdapter = new RecViewAdapter(podcasts, this);
        recyclerView.setAdapter(recViewAdapter);

    }

/*

    public void clearApplicationData()
    {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
        return dir.delete();
    }
*/


}
