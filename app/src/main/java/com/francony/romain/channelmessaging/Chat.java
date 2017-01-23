package com.francony.romain.channelmessaging;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Chat extends AppCompatActivity implements OnDowloadCompleteListener {

    private Connexion connexion;
    private ListView messagesListView;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("channelName"));
        messagesListView = (ListView) findViewById(R.id.listMessages);
        message = (EditText) findViewById(R.id.message);
        messagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this);

                builder.setMessage("ajouter ami")
                        .setTitle("Ajouter en ami");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=getmessages");
                HashMap<String,String> params = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
                params.put("accesstoken", settings.getString("token", "hello"));
                params.put("channelid",getIntent().getStringExtra("channelID"));
                connexion.setParmetres(params);
                connexion.setOnNewsUpdateListener(Chat.this);
                connexion.execute();
            }
        },500,1000);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //envoie message
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=sendmessage");
                HashMap<String,String> params = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
                params.put("accesstoken", settings.getString("token", "hello"));
                params.put("channelid",getIntent().getStringExtra("channelID"));
                params.put("message",message.getText().toString());
                message.setText("");
                connexion.setParmetres(params);
                connexion.execute();


            }
        });
    }

    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Messages messages = gson.fromJson(content,Messages.class);
        Collections.reverse(messages.getMessages());
        messagesListView.setAdapter(new MessageAdapter(getApplicationContext(),messages.getMessages()));
    }



}
