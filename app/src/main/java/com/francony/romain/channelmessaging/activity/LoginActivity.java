package com.francony.romain.channelmessaging.activity;

import android.Manifest;
import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.francony.romain.channelmessaging.task.Connexion;
import com.francony.romain.channelmessaging.task.OnDowloadCompleteListener;
import com.francony.romain.channelmessaging.R;
import com.francony.romain.channelmessaging.model.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements OnDowloadCompleteListener {

    private TextView login;
    private TextView password;
    private Button btnconnect;
    private TextView textAccueil;
    public static final String STOCKAGE = "MyPrefsFile";

    private static final String[] explainStringArray = {
            "Connecte toi pour chatter avec tes amis",
            "Salut à tous les gamers !",
            "Oh la triple headshot",
            "Yo tous le monde c'est Squezie",
            "AH !",
            "YEAH BITCH !! MAGNETS"
    };

    private View.OnClickListener mListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (TextView) findViewById(R.id.txtLogin);
        password = (TextView) findViewById(R.id.txtPassword);
        btnconnect = (Button) findViewById(R.id.btnConnect);
        textAccueil = (TextView) findViewById(R.id.textAccueil);

        mListener = new View.OnClickListener() {
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
                Animation animFadout = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_out);
                btnconnect.startAnimation(animFadout);


            }
        };
        btnconnect.setOnClickListener(mListener);






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

        final Handler mHandlerMessage = new Handler();
        mHandlerMessage.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.SlideOutRight).duration(750).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textAccueil.setText(explainStringArray[new Random().nextInt(explainStringArray.length)]);
                        YoYo.with(Techniques.SlideInLeft).duration(750).playOn(textAccueil);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).playOn(textAccueil);
                mHandlerMessage.postDelayed(this, 5000);
            }
        }, 5000);
        //u mad ?

        LinearLayout rl = (LinearLayout) findViewById(R.id.llBackground);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(rl)
                .setTransitionDuration(4000)
                .start();






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
        Response retour = null;
        try {
            retour = gson.fromJson(content, Response.class);
        }catch(Exception e) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container),"Connexion échoué", Snackbar.LENGTH_SHORT);
            mySnackbar.setAction("Réessayer!", mListener);
            mySnackbar.show();
        }


        if(retour != null && retour.getResponse().equals("Ok")){
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
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container),"Connexion échoué", Snackbar.LENGTH_SHORT);
            mySnackbar.setAction("Réessayer!", mListener);
            mySnackbar.show();
        }

        btnconnect.clearAnimation();
    }


}
