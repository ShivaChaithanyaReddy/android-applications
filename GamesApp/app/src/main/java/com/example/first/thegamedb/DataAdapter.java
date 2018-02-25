package com.example.first.thegamedb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class DataAdapter extends ArrayAdapter<Games> implements LogoAsync.IGames {
    List<Games> mData;
    Context mContext;
    int mResource;
    ImageView imageView;
    public DataAdapter(Context context, int resource, List<Games> objects){
        super(context, resource, objects);
        this.mContext = context;
        this.mData=objects;
        this.mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(mResource,parent,false);

        }


     //   new LogoAsync(this).execute(mData.get(position).getId());


        Games obj = mData.get(position);

        LinearLayout Llayout=(LinearLayout)convertView.findViewById(R.id.linear);


        imageView=(ImageView)convertView.findViewById(R.id.imagesView);
       // new getImageAsync(this,imageView).execute(obj.get);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(obj.getTitle().toString());



        return convertView;


    }

    @Override
    public void sendData(ArrayList<Games> arrayList) {

        Log.d("asdasdsadsad"," "+arrayList.get(0).getLogo());
       // new  getImageAsync(this,imageView).execute(arrayList.get(0).getLogo());
    }
}
