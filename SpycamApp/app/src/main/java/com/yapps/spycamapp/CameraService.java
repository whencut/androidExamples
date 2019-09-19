package com.yapps.spycamapp;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Environment;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class CameraService extends Service {
    static int x;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Parameters parameters;


    public CameraService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        camera=Camera.open();
        SurfaceView surfaceView=new SurfaceView(getApplicationContext());
        try {
            camera.setPreviewDisplay(surfaceView.getHolder());
            parameters=camera.getParameters();
            camera.setParameters(parameters);
            camera.startPreview();
            camera.takePicture(null,null,mcall);
        } catch (IOException e) {
            e.printStackTrace();
        }

        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    Camera.PictureCallback mcall=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream output;
            try {
                x++;
              output=new FileOutputStream(Environment.getExternalStorageDirectory()+"/image"+x+".jpg");
                output.write(data);
                output.close();
                camera.release();
                Toast.makeText(getApplicationContext(),"camera clicked",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
