package com.francony.romain.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements OnDowloadCompleteListener {

    private TextView login;
    private TextView password;
    private Button btnconnect;
    public static final String STOCKAGE = "MyPrefsFile";


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
                Toast.makeText(getApplicationContext(), "Connexion en cours", Toast.LENGTH_SHORT).show();
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("username",login.getText().toString());
                params.put("password",password.getText().toString());
                Connexion connexion = new Connexion("http://www.raphaelbischof.fr/messaging/?function=connect");
                connexion.setParmetres(params);
                connexion.setOnNewsUpdateListener(LoginActivity.this);
                connexion.execute();
            }
        });





    }


    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Response retour = gson.fromJson(content,Response.class);

        if(retour.getResponse().equals("Ok")){
            Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Channel.class);
            SharedPreferences settings = getSharedPreferences(STOCKAGE, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("token",retour.getAccesstoken());
            editor.commit();
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Connexion échouée", Toast.LENGTH_SHORT).show();
        }
    }
}
