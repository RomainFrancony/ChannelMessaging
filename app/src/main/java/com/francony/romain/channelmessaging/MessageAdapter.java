package com.francony.romain.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;

/**
 * Created by Romain on 21/01/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final ArrayList<Message> values;


    public MessageAdapter(Context context, ArrayList<Message> values){
        super(context,R.layout.item_channel, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        if (message.getMessageImageUrl().equals("")) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        }else{
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message_img, parent, false);
        }


        if (message.getMessageImageUrl().equals("")) {
            TextView messageView = (TextView) convertView.findViewById(R.id.message);
            messageView.setText(message.getMessage());

        }else{
            ImageView imgMessage = (ImageView) convertView.findViewById(R.id.imgMessage);
            File imgFile = new File(Environment.getExternalStorageDirectory()+"/Chat/img"+message.getMessageImageUrl().substring(message.getMessageImageUrl().lastIndexOf("/")));
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgMessage.setImageBitmap(myBitmap);
            }else{
                new GetImage(imgMessage).execute(message.getMessageImageUrl());
            }

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




}
