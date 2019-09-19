package com.yapps.spycamapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Venkat Vinay on 7/4/2016.
 */
public class AlarmBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"alarm", Toast.LENGTH_SHORT).show();
/*Intent intent2=new Intent(context,MainActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.getApplicationContext().startActivity(intent2);*/
        context.stopService(new Intent(context,CameraService.class));

    }
}
