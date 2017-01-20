package com.francony.romain.channelmessaging;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView login;
    private TextView password;
    private Button btnconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (TextView) findViewById(R.id.txtLogin);
        password = (TextView) findViewById(R.id.txtPassword);
        btnconnect = (Button) findViewById(R.id.btnConnect);

        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Connexion(getApplicationContext()).execute(5121L,8454L,68L) ;


            }
        });


    }



}
