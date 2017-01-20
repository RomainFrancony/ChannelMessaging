package com.francony.romain.channelmessaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class Channel extends AppCompatActivity implements OnDowloadCompleteListener {

    private String token;
    private Connexion connexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    }
}
