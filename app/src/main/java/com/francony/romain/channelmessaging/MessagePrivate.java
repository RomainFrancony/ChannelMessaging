package com.francony.romain.channelmessaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;

public class MessagePrivate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_private);

        Connexion connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=getmessages");
        HashMap<String,String> params = new HashMap<>();
        SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
        params.put("accesstoken", settings.getString("token", "hello"));
        params.put("userid",getIntent().getStringExtra("userid"));

        connexion.setParmetres(params);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
