package com.francony.romain.channelmessaging.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.francony.romain.channelmessaging.task.Connexion;
import com.francony.romain.channelmessaging.adapter.MessageAdapter;
import com.francony.romain.channelmessaging.task.OnDowloadCompleteListener;
import com.francony.romain.channelmessaging.R;
import com.francony.romain.channelmessaging.task.UploadFileToServer;
import com.francony.romain.channelmessaging.database.UserDataSource;
import com.francony.romain.channelmessaging.model.Message;
import com.francony.romain.channelmessaging.model.Messages;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Chat extends AppCompatActivity implements OnDowloadCompleteListener {

    private Connexion connexion;
    private ListView messagesListView;
    private EditText message;
    private Messages messagesBackup = new Messages();
    private MessageAdapter adapter;
    private final int PICTURE_REQUEST_CODE = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(getIntent().getStringExtra("channelName"));

        messagesListView = (ListView) findViewById(R.id.listMessages);
        adapter = new MessageAdapter(getApplicationContext(), new ArrayList<Message>());
        messagesListView.setAdapter(adapter);
        message = (EditText) findViewById(R.id.message);

        messagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Message user = (Message) messagesListView.getItemAtPosition(position);
                SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);
                String userIDConnected = settings.getString("login", "");

                if(!(user.getUsername().equals(userIDConnected)) ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this);

                    builder.setMessage("Ajouter "+user.getUsername()+" en amis .")
                            .setTitle("Ajouter un ami");

                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UserDataSource database = new UserDataSource(getApplicationContext());
                            database.open();

                            database.createUser(user.getUserID(),user.getUsername(),user.getImageUrl());
                            database.close();
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

        final FloatingActionButton photo = (FloatingActionButton) findViewById(R.id.imageSend);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.setEnabled(false);
                File test = new File(Environment.getExternalStorageDirectory()+"/Chat/img/img.jpg");
                try {
                    test.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try{
                    resizeFile(test,getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Android a depuis Android Nougat besoin d'un provider pour donner l'accès à un répertoire pour une autre app, cf : http://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
                Uri uri = FileProvider.getUriForFile(Chat.this, Chat.this.getApplicationContext().getPackageName() + ".provider", test);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Création de l’appelà l’application appareil photo pour récupérer une image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //Emplacement de l’image stockée
                startActivityForResult(intent, PICTURE_REQUEST_CODE);


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {



            case PICTURE_REQUEST_CODE :

                SharedPreferences settings = getSharedPreferences(LoginActivity.STOCKAGE, 0);


                List<NameValuePair> values = new ArrayList<NameValuePair>();
                values.add(new BasicNameValuePair("accesstoken",settings.getString("token","")));
                values.add(new BasicNameValuePair("channelid",getIntent().getStringExtra("channelID")));
                File test = new File(Environment.getExternalStorageDirectory()+"/Chat/img/img.jpg");
                try{
                    resizeFile(test,getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new UploadFileToServer(Chat.this,test.getPath() , values, new UploadFileToServer.OnUploadFileListener() {
                    @Override
                    public void onResponse(String result) {
                        Toast.makeText(getApplicationContext(),"Upload réussie",Toast.LENGTH_LONG);
                    }
                    @Override
                    public void onFailed(IOException error) {
                        Toast.makeText(getApplicationContext(),"upload failed",Toast.LENGTH_LONG);
                    }
                }).execute();
                final FloatingActionButton photo = (FloatingActionButton) findViewById(R.id.imageSend);
                photo.setEnabled(true);

        }
    }


    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        Messages messages = null;



        messages = gson.fromJson(content,Messages.class);
        Collections.reverse(messages.getMessages());
        if(!this.messagesBackup.getMessages().equals(messages.getMessages())){
            adapter.clear();
            adapter.addAll(messages.getMessages());
            adapter.notifyDataSetChanged();
        }
        this.messagesBackup =  messages;


    }





    //decodes image and scales it to reduce memory consumption
    public static void resizeFile(File f, Context context) throws IOException {
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

        //The new size we want to scale to
        final int REQUIRED_SIZE=400;

        //Find the correct scale value. It should be the power of 2.
        int scale=1;
        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
            scale*=2;

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize=scale;
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        int i = getCameraPhotoOrientation(context, Uri.fromFile(f),f.getAbsolutePath());
        if (o.outWidth>o.outHeight)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(i); // anti-clockwise by 90 degrees
            bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
        }
        try {
            f.delete();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) throws IOException {
        int rotate = 0;
        context.getContentResolver().notifyChange(imageUri, null);
        File imageFile = new File(imagePath);
        ExifInterface exif = new ExifInterface(
                imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        return rotate;
    }






}
