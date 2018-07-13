package com.example.shehandinuka.better_mind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;


public class UserPresentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Toast.makeText(context, "We Always with you..!",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, CamaraService.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

          else if(intent.getAction().equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                Log.i(UserPresentBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
                context.startService(new Intent(context, InvokeCamesrService.class));
            }
       }
}


