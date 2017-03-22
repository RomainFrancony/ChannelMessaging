package com.francony.romain.channelmessaging.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.francony.romain.channelmessaging.model.ChannelClass;
import com.francony.romain.channelmessaging.model.Channels;
import com.francony.romain.channelmessaging.adapter.ListeArrayAdapter;
import com.francony.romain.channelmessaging.task.OnDowloadCompleteListener;
import com.francony.romain.channelmessaging.R;
import com.francony.romain.channelmessaging.fragments.ChannelListFragment;
import com.francony.romain.channelmessaging.fragments.MessageFragment;
import com.google.gson.Gson;

public class Channel extends AppCompatActivity implements OnDowloadCompleteListener, AdapterView.OnItemClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int display_mode = getResources().getConfiguration().orientation;

        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {

            setContentView(R.layout.activity_channel);
        } else {
            setContentView(R.layout.chat_land);
        }
    }

    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Channels listeChannels = gson.fromJson(content,Channels.class);
        ChannelListFragment fragA = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentChannel);
        fragA.listView.setAdapter(new ListeArrayAdapter(getApplicationContext(),listeChannels.getChannels()));

    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelListFragment fragA = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentChannel);
        MessageFragment fragB = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMessage);
        TextView idText = (TextView) view.findViewById(R.id.idChannel) ;
        ChannelClass channel = (ChannelClass) idText.getTag();
        if(fragB == null|| !fragB.isInLayout()){
            Intent i = new Intent(getApplicationContext(),Chat.class);
            i.putExtra("channelID",channel.getChannelID());
            i.putExtra("channelName",channel.getName());
            startActivity(i);
        } else {
            fragB.getChannelID(channel.getChannelID());
        }
    }


}
