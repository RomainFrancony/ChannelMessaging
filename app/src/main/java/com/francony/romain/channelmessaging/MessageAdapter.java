package com.francony.romain.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Romain on 21/01/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final ArrayList<Message> values;
    private Button btnplay;

    public MessageAdapter(Context context, ArrayList<Message> values){
        super(context,R.layout.item_channel, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  Message message = getItem(position);


        if(!message.getSoundUrl().equals("")){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message_sound, parent, false);


            btnplay =(Button) convertView.findViewById(R.id.btnPlayMessage);
            btnplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final File soundFile = new File(Environment.getExternalStorageDirectory()+"/Chat/sound"+message.getSoundUrl().substring(message.getSoundUrl().lastIndexOf("/")));
                     GetSound getter = new GetSound(message.getSoundUrl(),soundFile.getAbsolutePath());
                    if(!soundFile.exists()){
                        getter.execute();
                    }else{
                        startPlaying(soundFile.getAbsolutePath());
                    }

                    getter.setOnNewsUpdateListener(new OnDowloadCompleteListener() {
                        @Override
                        public void onDownloadComplete(String content) {
                            startPlaying(soundFile.getAbsolutePath());
                        }
                    });



                }
            });


        }
        if(!message.getMessageImageUrl().equals("")){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message_img, parent, false);
                ImageView imgMessage = (ImageView) convertView.findViewById(R.id.imgMessage);
                File imgFile = new File(Environment.getExternalStorageDirectory()+"/Chat/img"+message.getMessageImageUrl().substring(message.getMessageImageUrl().lastIndexOf("/")));
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imgMessage.setImageBitmap(myBitmap);
                }else{
                    new GetImage(imgMessage).execute(message.getMessageImageUrl());
                }
        }
        if(message.getMessageImageUrl().equals("") && message.getSoundUrl().equals("")){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
                TextView messageView = (TextView) convertView.findViewById(R.id.message);
                messageView.setText(message.getMessage());
        }

        TextView name = (TextView) convertView.findViewById(R.id.username);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        TextView date = (TextView) convertView.findViewById(R.id.dateText);

        date.setText(message.getDate());

        name.setText(message.getUsername());
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/Chat/img"+message.getImageUrl().substring(message.getImageUrl().lastIndexOf("/")));

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img.setImageBitmap(myBitmap);
        }else{
            new GetImage(img).execute(message.getImageUrl());
        }

        return convertView;
    }

    private MediaPlayer mPlayer;
    private void startPlaying(String mFileName) {
        btnplay.setText("STOP");
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {

        }


        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnplay.setText("PLAY");
            }
        });
    }





}
