package com.francony.romain.channelmessaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Romain on 20/01/2017.
 */

public class ListeArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ListeArrayAdapter(Context context, String[] values){
        super(context,R.layout.content_channel, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.content_channel, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.channelName);
        TextView nb = (TextView) rowView.findViewById(R.id.nbConnect);

        name.setText("yolooo");
        nb.setText("blbla");

        return rowView;
    }

}
