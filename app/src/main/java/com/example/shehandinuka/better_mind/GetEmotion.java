package com.example.shehandinuka.better_mind;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceAttribute;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



//in this code i'm create a connection with Emotion api and send a data into the api as dyte array;
// and check the time is lessthan or equal to the 60 min in order to send the pic into the api

class GetEmotion {
    public static String current;

     public static double happiness;
     public static  double sadness;
     public static  double neutral;
    static AsyncTask <InputStream, String, Face[]> detectTask;
    FaceServiceClient faceServiceClient = new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "3ae601c7df4e49e19e8062d8ea613053");
    ByteArrayOutputStream baos;


    @SuppressLint("StaticFieldLeak")
    public void calltotheapi(){

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = path + "/" + "bettermind" + "/" + "test.jpg";
        Bitmap bm = BitmapFactory.decodeFile(path);
        baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        ByteArrayInputStream inputStream= new ByteArrayInputStream(baos.toByteArray());

    detectTask =new AsyncTask <InputStream, String, Face[]>() {

        @Override
        protected Face[] doInBackground(InputStream... inputStreams) {


            try {
                Face[] result = faceServiceClient.detect(inputStreams[0], true, true, new FaceServiceClient.FaceAttributeType[]{FaceServiceClient.FaceAttributeType.Emotion, FaceServiceClient.FaceAttributeType.Age});
                System.out.println("works");
                if (result == null) {
                    return null;
                }
                return result;

            } catch (ClientException e) {
                Log.e("ClientException", e.toString());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Face[] faces) {
            if(faces == null){
                System.out.println("face is null");
            }
            attributeMethod(faces);
        }
    };


   detectTask.execute(inputStream);

}


        private void attributeMethod(Face[] faces) {

            for (Face face : faces) {
                FaceAttribute attribute = face.faceAttributes;
                Emotion result = attribute.emotion;//take the Facial Emotion;
                happiness = result.happiness;
                sadness =  result.sadness;
                neutral = result.neutral;System.out.println("Emotions are " + happiness + "," + sadness + "," + neutral);

            }
            SendUserEmotion sue = new SendUserEmotion();
            sue.execute();
        }

    }
