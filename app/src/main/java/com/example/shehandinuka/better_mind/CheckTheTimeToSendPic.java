package com.example.shehandinuka.better_mind;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;



public class CheckTheTimeToSendPic extends AsyncTask<Void,Void,String> {

    public  String timeIntheServer;
    String user_id = "5b0c2d116dcfd443a05d8515";

    @Override
    protected String doInBackground(Void... voids) {

        String url = "https://bettermind-204701.appspot.com/time/";
        try {
            String data = URLEncoder.encode(user_id, "UTF-8");
            url += data;
            URL obj = new URL(url); // here is your URL path

            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");



            System.out.println("Sending 'POST' request to URL : " + url);
            int responseCode = conn.getResponseCode();
            //System.out.println("response code "+responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                return sb.toString();

            } else return ("false : " + responseCode);
        } catch (Exception e) {
            return ("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println("recieve json"+s);
        try {
            JSONArray jsonArray = new JSONArray(s);
            JSONObject jobj = jsonArray.getJSONObject(0);
            timeIntheServer = jobj.getString("p_time");
            System.out.println("p_time "+timeIntheServer);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
        try {
            getEmotion();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getEmotion() throws ParseException {
        String currentTime;
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm a");
        currentTime = sdf.format(d);

        Date date1 = null;
        Date date2 = null;
        try {

            System.out.println("current "+ currentTime);
            date1 = sdf.parse(currentTime);
            date2 = sdf.parse(timeIntheServer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("server time "+date1.getTime());
        long difference = date1.getTime()- date2.getTime();
        System.out.println("difference "+difference);
        GetEmotion getEmotion = new GetEmotion();
        getEmotion.calltotheapi();
        //}
    }
}