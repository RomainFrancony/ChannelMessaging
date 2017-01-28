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
import java.util.List;

/**
 * Created by franconr on 27/01/2017.
 */
public class FriendAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> values;

    public FriendAdapter(Context context, List<User> values){
        super(context,R.layout.item_friend, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, parent, false);
        }
        TextView username = (TextView) convertView.findViewById(R.id.username);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgFriend);

        username.setText(user.getUsername());
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/Chat/img"+user.getImageUrl().substring(user.getImageUrl().lastIndexOf("/")));

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img.setImageBitmap(myBitmap);
        }else{
            new GetImage(img).execute(user.getImageUrl());
        }

        return convertView;
    }
}
