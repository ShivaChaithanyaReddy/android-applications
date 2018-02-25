package com.example.brandon.inclass10;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ChatRoom extends AppCompatActivity implements RecViewAdapter.IActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;


    //for Recview
    private RecyclerView recyclerView;
    private RecViewAdapter recViewAdapter;
    private GridLayoutManager mLayoutManager;
    // for Recview

private  String selectedImagePath;
    static ArrayList<Messages> arrayList;
    String nameOfUser;
    private static final int SELECT_PICTURE = 1;

    TextView username;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        arrayList = new ArrayList<>();

        final String userID = getIntent().getStringExtra(MainActivity.USER);

        username = (TextView) findViewById(R.id.username);



        findViewById(R.id.sendImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // in onCreate or any event where your want the user to
                // select a file
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Signup s = postSnapshot.getValue(Signup.class);
                    Log.d("In chat", "Value is: " + postSnapshot.getValue());
                    if(postSnapshot.getKey().equals(userID)) {
                        username.setText(s.getFname());
nameOfUser = s.getFname();
                    }
                }

                arrayList.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.child("Messages").getChildren()) {

             arrayList.add(dataSnapshot1.getValue(Messages.class));

                    Log.d("array size",""+arrayList.size());
                }            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



    message = (EditText) findViewById(R.id.message);



        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg =  message.getText().toString();

                String nn = nameOfUser;

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                String DateNow = dateFormat.format(date);

                String image = "empty";
                String kk = ""+UUID.randomUUID();

                Messages m = new Messages(image , msg,nn,DateNow,kk,null);


                myRef.child("Messages").child(kk).setValue(m);

              //  arrayList.add(m);
                recViewAdapter.notifyDataSetChanged();

            }
        });


        //RecyclerView Code starts:
        recyclerView = (RecyclerView) findViewById(R.id.recView);


        LinearLayoutManager mgridLayoutManager = new LinearLayoutManager(ChatRoom.this);
        recyclerView.setLayoutManager(mgridLayoutManager);


        recViewAdapter = new RecViewAdapter(arrayList, this,this);
        recyclerView.setAdapter(recViewAdapter);
        //RecyclerView Code ends:

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                File imgFile = new File(selectedImageUri.toString());
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] dataa = baos.toByteArray();
                    String path = "firebaseI"+UUID.randomUUID()+"JPEG";
                    StorageReference sr = FirebaseStorage.getInstance().getReference(path);


                    Log.d("aaaaaaaaaaaaaaaaaaaaaa","vvvvvvvvvvv");
                    UploadTask uploadTask = sr.putBytes(dataa);

                    final Uri[] uri = new Uri[1];
                    uploadTask.addOnSuccessListener(ChatRoom.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             uri[0] = taskSnapshot.getDownloadUrl();
                            Log.d("aaaaaaaaaaa", uri[0].toString());
                        }
                    });

                    //Drawable d = new BitmapDrawable(getResources(), myBitmap);
//                    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
//                    myImage.setImageBitmap(myBitmap);

                }

                String nn = nameOfUser;

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                String DateNow = dateFormat.format(date);

                String image = selectedImageUri+"";
                String kk = ""+UUID.randomUUID();

                Messages m = new Messages(image , null,nn,DateNow,kk,null);


                myRef.child("Messages").child(kk).setValue(m);

                //  arrayList.add(m);
                recViewAdapter.notifyDataSetChanged();


                Log.d("ashhhhhhhhhhhadsds",""+selectedImageUri);
            }
        }
    }

    //UPDATED!
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }

    @Override
    public void senddata(int pos) {

    }
}
