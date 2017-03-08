package com.francony.romain.channelmessaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Romain on 20/01/2017.
 */

public class ListeArrayAdapter extends ArrayAdapter<ChannelClass> {
    private final Context context;
    private final ArrayList<ChannelClass> values;

    public ListeArrayAdapter(Context context, ArrayList<ChannelClass> values){
        super(context,R.layout.item_channel, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChannelClass channel = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_channel, parent, false);
        }
        TextView nb = (TextView) convertView.findViewById(R.id.nbConnect);
        TextView name = (TextView) convertView.findViewById(R.id.channelName);
        name.setText();
        nb.setText("Nombre de personnes connectés : "+channel.getConnectedusers().toString());

        TextView id = (TextView) convertView.findViewById(R.id.idChannel);
        id.setTag(channel);

        if(position==0){
            System.out.println("-----------------------------------------------");
            System.out.println(channel.getName().toString());
            convertView.setSelected(true);
        }


        return convertView;
    }

}
