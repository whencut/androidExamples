package com.yapps.spycamapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main2Activity.this,"set",Toast.LENGTH_LONG).show();
                repeatingAlaram();
                //startService(new Intent(getBaseContext(),CameraService.class));
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      cancelAlaram();
                //stopService(new Intent(getApplicationContext(),CameraService.class));
            }
        });
    }

    public void repeatingAlaram() {

        Intent intent=new Intent(this,CameraService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //System.currentTimeMillis()
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),20*1000  , pendingIntent);

        // Toast.makeText(this, "Alarm set in " + 20 + " seconds",Toast.LENGTH_LONG).show();
    }
    public void cancelAlaram() {

        Intent intent = new Intent(this, AlarmBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //System.currentTimeMillis()
        alarmManager.cancel(pendingIntent);

         Toast.makeText(this, "Alarm stopped",Toast.LENGTH_LONG).show();
    }

}
