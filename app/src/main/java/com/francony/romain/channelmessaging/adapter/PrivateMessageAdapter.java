package com.francony.romain.channelmessaging.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.francony.romain.channelmessaging.task.GetImage;
import com.francony.romain.channelmessaging.model.PrivateMessageClass;
import com.francony.romain.channelmessaging.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Romain on 28/01/2017.
 */

public class PrivateMessageAdapter extends ArrayAdapter<PrivateMessageClass> {
    private final Context context;
    private final ArrayList<PrivateMessageClass> values;


    public PrivateMessageAdapter(Context context, ArrayList<PrivateMessageClass> values){
        super(context, R.layout.message_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getItemViewType(int position) {
        PrivateMessageClass message = getItem(position);
        return message.getSendbyme().equals("1")?1:0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PrivateMessageClass message = getItem(position);

            if(getItemViewType(position)==1)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item_right, parent, false);
            }else{

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
            }


        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.messagelayout);

        TextView messageView = (TextView) convertView.findViewById(R.id.message);
        TextView name = (TextView) convertView.findViewById(R.id.username);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        TextView date = (TextView) convertView.findViewById(R.id.dateText);
        date.setText(message.getDate());
        messageView.setText(message.getMessage());
        name.setText(message.getUsername());
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/Chat/img"+message.getImageUrl().substring(message.getImageUrl().lastIndexOf("/")));


        if(message.getEverRead().equals("0") && message.getSendbyme().equals("0")) {
            messageView.setTypeface(Typeface.DEFAULT_BOLD);
        }



        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img.setImageBitmap(myBitmap);
        }else{
            new GetImage(img).execute(message.getImageUrl());
        }



        return convertView;
    }
}
