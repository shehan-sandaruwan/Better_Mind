package com.example.shehandinuka.better_mind;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


// if getemotion class invoked then this class will invoke and update the time in the cloud relevent to the particular user
public class UpdateServerTime extends AsyncTask<Void,Void,Void> {

    String user_id= "5b0c2d116dcfd443a05d8515";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String url = "https://bettermind-204701.appspot.com/time/";
        URL obj = null;
        try {
            obj = new URL(url);

        HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
            conn.setRequestMethod("PUT");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);


        //add reuqest header
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("user_id", user_id));


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(params.toString());
            writer.flush();
            writer.close();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
