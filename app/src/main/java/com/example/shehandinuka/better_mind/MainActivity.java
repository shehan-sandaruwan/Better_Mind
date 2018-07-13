package com.example.shehandinuka.better_mind;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    UserPresentBroadcastReceiver userPresentBroadcastReceiver = new UserPresentBroadcastReceiver();

    Intent mServiceIntent;
    private  InvokeCamesrService invokeCamesrService;
    Context ctx;
    public Context getCtx(){
        return  ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takepicture);

        IntentFilter intentFilter = new IntentFilter("com.example.shehandinuka.better_mind");
        registerReceiver(userPresentBroadcastReceiver, intentFilter);

        ctx = this;
        invokeCamesrService = new InvokeCamesrService(getCtx());
        mServiceIntent = new Intent(getCtx(),invokeCamesrService.getClass());

        if(!isMyServiceRunning(invokeCamesrService.getClass())){
            startService(mServiceIntent);
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }
}




