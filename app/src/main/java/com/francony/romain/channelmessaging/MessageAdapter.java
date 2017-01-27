package com.francony.romain.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
        }
        TextView messageView = (TextView) convertView.findViewById(R.id.message);
        TextView name = (TextView) convertView.findViewById(R.id.username);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        TextView date = (TextView) convertView.findViewById(R.id.dateText);
        date.setText(message.getDate());
        messageView.setText(message.getMessage());
        name.setText(message.getUsername());
        File imgFile = new File(Environment.getExternalStorageDirectory()+message.getImageUrl().substring(message.getImageUrl().lastIndexOf("/")));


        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img.setImageBitmap(myBitmap);
        }else{
            new GetImage(img).execute(message.getImageUrl());
        }
        return convertView;
    }




}
