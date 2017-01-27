package com.francony.romain.channelmessaging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.HashMap;

public class Friend extends AppCompatActivity {
    private GridView gridUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        gridUser =(GridView) findViewById(R.id.friendsGrid);

        UserDataSource database = new UserDataSource(getApplicationContext());
        database.open();
        gridUser.setAdapter(new FriendAdapter(getApplicationContext(),database.getAllUser()));
        database.close();

        gridUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) gridUser.getItemAtPosition(position);

                //Intent intent = new Intent()


            }
        });
    }
}
