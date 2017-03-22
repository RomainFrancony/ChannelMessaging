package com.francony.romain.channelmessaging.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.francony.romain.channelmessaging.adapter.FriendAdapter;
import com.francony.romain.channelmessaging.R;
import com.francony.romain.channelmessaging.database.UserDataSource;
import com.francony.romain.channelmessaging.model.User;

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

                Intent intent = new Intent(getApplicationContext(),MessagePrivate.class);
                intent.putExtra("userid",Integer.toString(user.getUserID()));
                startActivity(intent);


            }
        });
    }
}
