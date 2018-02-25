package com.example.first.notekeeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import org.w3c.dom.Text;
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.List;

/**
 * Created by Chaithanya on 2/22/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    List<Note> mData;
    Context mContext;
    int mResource;
    INote iNote;

    DatabaseDataManager dm;



    SharedPreferences sharedPreferences;

    public NoteAdapter(Context context, int resource, List<Note> objects, INote iNote) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
        this.iNote  = iNote;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Note note = mData.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(note.getName());

        TextView status = (TextView) convertView.findViewById(R.id.checkBox);
        status.setText(note.getStatus());

        TextView priority = (TextView) convertView.findViewById(R.id.priority);
        priority.setText(note.getPriority());

        TextView time  = (TextView) convertView.findViewById(R.id.time);
        Long t = Long.parseLong(note.getTime());
        Long at = NANOSECONDS.toSeconds(System.nanoTime()/1000);
        PrettyTime pt = new PrettyTime(new Date(at));
        String ptf = pt.format(new Date(0));
        time.setText(ptf);

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        checkBox.setText("");


        if(note.getStatus().equals("completed"))
            checkBox.setChecked(true);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iNote.onCheckClick(position);
            }
        });



        return convertView;
    }

    public interface INote {
        void onCheckClick(int position);
    }
}
