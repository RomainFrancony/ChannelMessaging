package com.francony.romain.channelmessaging.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.francony.romain.channelmessaging.task.Connexion;
import com.francony.romain.channelmessaging.task.OnDowloadCompleteListener;
import com.francony.romain.channelmessaging.adapter.PrivateMessageAdapter;
import com.francony.romain.channelmessaging.model.PrivateMessageClass;
import com.francony.romain.channelmessaging.model.PrivateMessages;
import com.francony.romain.channelmessaging.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MessagePrivate extends AppCompatActivity implements OnDowloadCompleteListener {
    private ListView messageList;
    private EditText messageMp;

    private ArrayList<PrivateMessageClass> messagesBackup = new ArrayList<>();
    private PrivateMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_private);

        adapter = new PrivateMessageAdapter(getApplicationContext(), new ArrayList<PrivateMessageClass>());
        messageList = (ListView) findViewById(R.id.listMP);
        messageMp = (EditText) findViewById(R.id.mpInput);
        messageList.setAdapter(adapter);



        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Connexion connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=getmessages");
                HashMap<String,String> params = new HashMap<>();
                SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
                params.put("accesstoken", settings.getString("token", "hello"));
                params.put("userid",getIntent().getStringExtra("userid"));
                connexion.setParmetres(params);
                connexion.setOnNewsUpdateListener(MessagePrivate.this);
                connexion.execute();
            }
        },500,1000);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connexion connexionenvoie = new Connexion("http://www.raphaelbischof.fr/messaging/?function=sendmessage");
                HashMap<String,String> params = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
                params.put("accesstoken", settings.getString("token", "hello"));
                params.put("userid",getIntent().getStringExtra("userid"));
                params.put("message",messageMp.getText().toString());

                messageMp.setText("");
                connexionenvoie.setParmetres(params);
                connexionenvoie.execute();
            }
        });




    }



    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        PrivateMessages messages = gson.fromJson(content,PrivateMessages.class);

        Collections.reverse(messages.getMessages());

        if(!this.messagesBackup.equals(messages.getMessages())){
            adapter.clear();
            adapter.addAll(messages.getMessages());
            adapter.notifyDataSetChanged();
        }
        PrivateMessages messages2 = gson.fromJson(content,PrivateMessages.class);
        this.messagesBackup =  messages2.getMessages();
    }





}
