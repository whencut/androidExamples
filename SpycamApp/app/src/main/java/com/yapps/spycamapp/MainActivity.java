package com.yapps.spycamapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView= (ImageView) findViewById(R.id.iv);

        Intent intent1 = new Intent(android.provider.MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivityForResult(intent1,999);

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  finish();
                }
            },1000);

        }
   /* else {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }*/}

}
