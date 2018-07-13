package com.example.shehandinuka.better_mind;



import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;



class MyserviceClass extends AsyncTask<Void,Void,String>{

    static String user_id = "5b0c2d116dcfd443a05d8515";
    public static double aSad_val,aHappy_val,aNeutral_val;
     static List<Double> sadVal = new ArrayList<>();
     static  List<Double> happyVal = new ArrayList<>();
     static List<Double> neutralVal = new ArrayList<>();
     static  List<String> time = new ArrayList <>();

        @Override
        protected String doInBackground(Void... voids) {

            String url = "https://bettermind-204701.appspot.com/emotion/";
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

            double count1=0,count2=0,count3=0;

            try {
                JSONObject jobj = new JSONObject(s);
                JSONArray jsonArray = jobj.getJSONArray("result");
                System.out.println("Jsonarray"+jsonArray);
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject job = jsonArray.getJSONObject(i);
                    sadVal.add(Double.valueOf(job.getString("sad_val")));
                    happyVal.add(Double.valueOf(job.getString("happy_val")));
                    neutralVal.add(Double.valueOf(job.getString("neutral_val")));
                    time.add(job.getString("time"));
                }
                for(int j=0;j<sadVal.size();j++){
                    count1 = count1 + sadVal.get(j);
                    count2 = count2 + happyVal.get(j);
                    count3 = count3 + neutralVal.get(j);
                }
                aSad_val = count1/sadVal.size();
                aNeutral_val = count3/sadVal.size();
                aHappy_val = count2/sadVal.size();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

