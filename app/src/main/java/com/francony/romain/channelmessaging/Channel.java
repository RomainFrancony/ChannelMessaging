package com.francony.romain.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Channel extends AppCompatActivity implements OnDowloadCompleteListener, AdapterView.OnItemClickListener {

    private String token;
    private Connexion connexion;
    private ListView listView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_channel);



        fab =(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Friend.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.channels);
        listView.setOnItemClickListener(this);
        connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=getchannels");
        HashMap<String,String> params = new HashMap<>();
        SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
        params.put("accesstoken", settings.getString("token", "hello"));
        connexion.setParmetres(params);
        connexion.setOnNewsUpdateListener(Channel.this);
        connexion.execute();
    }

    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Channels listeChannels = gson.fromJson(content,Channels.class);
        listView.setAdapter(new ListeArrayAdapter(getApplicationContext(),listeChannels.getChannels()));
    }


    //On click item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView idText = (TextView) view.findViewById(R.id.idChannel) ;
        ChannelClass channel = (ChannelClass) idText.getTag();
        Intent intent = new Intent(getApplicationContext(),Chat.class);
        intent.putExtra("channelID",channel.getChannelID());
        intent.putExtra("channelName",channel.getName());
        startActivity(intent);
    }
}
