package com.example.shehandinuka.better_mind;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

// captured user emotion will send to the server
public class SendUserEmotion extends AsyncTask<Void,Void,Void> {
    private double sad_val,happy_val,neutral_val;
    String user_id = "5b0c2d116dcfd443a05d8515";
    InputStream inputStream;
    HttpURLConnection urlConnection;
    byte[] outputBytes;
    String query;
    String ResponseData;

    @Override
    protected void onPreExecute() {
        sad_val = GetEmotion.sadness;
        happy_val = GetEmotion.happiness;
        neutral_val = GetEmotion.neutral;

        System.out.println("neutral "+neutral_val);

        String currentTime;
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm a");
        currentTime = sdf.format(d);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("user_id",user_id)
                .appendQueryParameter("sad_val", String.valueOf(sad_val))
                .appendQueryParameter("happy_val", String.valueOf(happy_val))
                .appendQueryParameter("neutral_val", String.valueOf(neutral_val))
                .appendQueryParameter("time", String.valueOf(currentTime));


        query = builder.build().getEncodedQuery();

    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {

        /* forming th java.net.URL object */
            URL url = new URL("https://bettermind-204701.appspot.com/emotion/");
            urlConnection = (HttpURLConnection) url.openConnection();


            /* pass post data */
            outputBytes = query.getBytes("UTF-8");

            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

            // Get Response and execute WebService request
            int statusCode = urlConnection.getResponseCode();
            System.out.println("status code"+statusCode);

        /* 200 represents HTTP OK */
            if (statusCode == HttpsURLConnection.HTTP_OK) {

                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                ResponseData= String.valueOf(inputStream);
                System.out.println("response data"+ResponseData);

            } else {

                ResponseData = null;
            }


        } catch (Exception e) {

            e.printStackTrace();

        }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MyserviceClass myserviceClass = new MyserviceClass();
        myserviceClass.execute();
    }
}

