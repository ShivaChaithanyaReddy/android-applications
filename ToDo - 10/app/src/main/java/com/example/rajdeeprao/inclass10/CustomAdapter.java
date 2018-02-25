package com.example.rajdeeprao.inclass10;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rajdeeprao on 3/11/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    int resource;
    List<Note> objects;
    IData objectMainActivity;

    public CustomAdapter(Context context, int resource, ArrayList<Note> notesList) {
        this.context = context;
        this.resource = resource;
        objects=notesList;
        objectMainActivity= (IData) context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView titleText;
        TextView dateText;
        CheckBox statusText;
        TextView priorityText;


        public ViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.TitleTV);
            dateText = (TextView) itemView.findViewById(R.id.TimeTV);
            statusText = (CheckBox) itemView.findViewById(R.id.checkBox);
            priorityText = (TextView) itemView.findViewById(R.id.PriorityTv);



        }

        @Override
        public boolean onLongClick(View v) {
            Log.d("TAG",getPosition()+"");
            return false;
        }




    }

    /*@NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resource,parent,false);
        }

        TextView tv1= (TextView) convertView.findViewById(R.id.title);
        tv1.setText(objects.get(position).getTitle());

        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(context)
                .load(objects.get(position).getImageURL())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {

                    }
                });

        TextView tv2= (TextView) convertView.findViewById(R.id.date);
        tv2.setText(objects.get(position).getPublicationDate());

        return convertView;
    }*/

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);
        ViewHolder vh=new ViewHolder(itemView);
        return vh;

    }

    @Override
    public void onBindViewHolder(final CustomAdapter.ViewHolder holder, final int position) {

        holder.itemView.setLongClickable(true);
        final Note note= objects.get(position);
        PrettyTime p=new PrettyTime();
        Date dateFormat=new Date(note.getUpdate_time());
        holder.titleText.setText(note.getNote());
        holder.priorityText.setText(note.getPriority());
        holder.dateText.setText(p.format(dateFormat).toString());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("Object",note.get_id() + "");
                objectMainActivity.returnValues((int)note.get_id());
                return false;
            }
        });

        holder.statusText.setChecked(note.getStatus() == "COMPLETED");
        holder.statusText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if (isChecked != true) {
                    builder.setMessage("Do you want to mark it as Pending")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LinearLayout parent = (LinearLayout) buttonView.getParent();
                                    TextView note = (TextView) parent.getChildAt(0);
                                    String noteValue = note.getText().toString();
                                    LinearLayout root = (LinearLayout) parent.getParent();
                                    LinearLayout parent2 = (LinearLayout) root.getChildAt(1);
                                    TextView priority = (TextView) parent.getChildAt(0);
                                    String priorityValue = priority.getText().toString();
                                    TextView time = (TextView) parent2.getChildAt(1);
                                    String timeValue = time.getText().toString();

                                    // MainActivity.dm.saveNote(new Note(noteValue,"PENDING",priorityValue,timeValue));

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.statusText.setChecked(!isChecked);
                                    Log.d("NO: ", "Clicked No");
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    builder.setMessage("Do you want to mark it as Completed?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LinearLayout parent = (LinearLayout) buttonView.getParent();
                                    TextView note = (TextView) parent.getChildAt(0);
                                    String noteValue = note.getText().toString();
                                    LinearLayout root = (LinearLayout) parent.getParent();
                                    LinearLayout parent2 = (LinearLayout) root.getChildAt(1);
                                    TextView priority = (TextView) parent.getChildAt(0);
                                    String priorityValue = priority.getText().toString();
                                    TextView time = (TextView) parent2.getChildAt(1);
                                    String timeValue = time.getText().toString();
                                    //MainActivity.dm.saveNote(new Note(noteValue,"COMPLETED",priorityValue,timeValue));

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("NO: ", "Clicked No");
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public interface IData{
        void returnValues(int id);
    }
}

