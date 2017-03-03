package com.francony.romain.channelmessaging;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
                Animation animSlideLeft = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.slide_left);
                TextView txt = (TextView) findViewById(R.id.textAccueil);
                txt.clearAnimation();
                txt.startAnimation(animSlideLeft);
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






        //FloatingActionButton mapBtn = (FloatingActionButton) findViewById(R.id.fabmap);

        /*mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GPSActivity.class);
                startActivity(intent);
            }
        });*/




        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        final Handler mHandlerTada = new Handler(); // android.os.handler
        final int mShortDelay = 4000; //milliseconds

        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(findViewById(R.id.imageAccueil));
                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                    finish();
                }
                return;
            }
        }
    }






    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Response retour = gson.fromJson(content,Response.class);



        if(retour.getResponse().equals("Ok")){
            Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();

            SharedPreferences settings = getSharedPreferences(STOCKAGE, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("token",retour.getAccesstoken());
            editor.putString("login",login.getText().toString());
            editor.commit();
            ImageView mIvLogo = (ImageView) findViewById(R.id.imageAccueil);
            Intent loginIntent = new Intent(LoginActivity.this, Channel.class);
            startActivity(loginIntent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mIvLogo, "logo").toBundle());

        }else{
            Toast.makeText(getApplicationContext(), "Connexion échouée", Toast.LENGTH_SHORT).show();
        }
    }
}
