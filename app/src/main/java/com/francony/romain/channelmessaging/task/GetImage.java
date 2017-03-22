package com.francony.romain.channelmessaging.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Romain on 21/01/2017.
 */

public class GetImage extends AsyncTask<String,Void,Bitmap> {
    private ImageView img;
    private int nb =0;
    private String url ="";


    public GetImage(ImageView image){
        this.img = image;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        this.url = urls[0];
        Bitmap imageRetour = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            imageRetour = BitmapFactory.decodeStream(in);
        } catch (Exception e) {

            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }



        return imageRetour;
    }

    protected void onPostExecute(Bitmap result) {


        FileOutputStream out = null;
        try {
            out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Chat/img"+this.url.substring(this.url.lastIndexOf("/")));
            result.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }


            img.setImageBitmap(result);




    }



}
