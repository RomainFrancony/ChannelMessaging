package com.francony.romain.channelmessaging;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Romain on 21/01/2017.
 */

public class GetSound extends AsyncTask<String,Void,String> {
    private final String fileUrl;
    private final String filename;

    private ArrayList<OnDowloadCompleteListener> listeners = new ArrayList<OnDowloadCompleteListener>();
    public void setOnNewsUpdateListener (OnDowloadCompleteListener listener)
    {
        this.listeners.add(listener);
    }


    public GetSound(String fileUrl, String filename){

        this.fileUrl = fileUrl;
        this.filename = filename;
    }

    protected String doInBackground(String... urls) {
        downloadFromUrl(fileUrl, filename);

        return "ah";
    }

    protected void onPostExecute(String result) {
        for (OnDowloadCompleteListener oneListener : listeners)
        {
            oneListener.onDownloadComplete("download complete");
        }
    }



    public void downloadFromUrl(String fileURL, String fileName) {
        try {
            URL url = new URL( fileURL);
            File file = new File(fileName);
            file.createNewFile();
            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
             /* Define InputStreams to read from the URLConnection.*/
            InputStream is = ucon.getInputStream();
             /* Read bytes to the Buffer until there is nothing more to read(-1) and write on the fly in the file.*/
            FileOutputStream fos = new FileOutputStream(file);
            final int BUFFER_SIZE = 23 * 1024;
            BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
            byte[] baf = new byte[BUFFER_SIZE];
            int actual = 0;
            while (actual != -1) {
                fos.write(baf, 0, actual);
                actual = bis.read(baf, 0, BUFFER_SIZE);
            }
            fos.close();
        } catch (IOException e) {
            //TODO HANDLER
            System.out.println(e.toString());
        }
    }



    }




