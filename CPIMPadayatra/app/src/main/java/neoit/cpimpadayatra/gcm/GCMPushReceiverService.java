package neoit.cpimpadayatra.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.gcm.GcmListenerService;

import java.util.concurrent.ExecutionException;

import neoit.cpimpadayatra.R;
import neoit.cpimpadayatra.views.MainActivity;


public class GCMPushReceiverService extends GcmListenerService {
    private Bitmap theBitmap = null;
    String TAG="HIII";
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String imgurl = data.getString("imgurl");
        String title = data.getString("title");
        try {
            theBitmap = Glide.
                    with(this).
                    load("https://www.google.es/images/srpr/logo11w.png").
                    asBitmap().
                    into(-1,-1).
                    get();
        } catch (final ExecutionException e) {
            Log.e(TAG, e.getMessage());
        } catch (final InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
        sendNotification(message);
    }
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;//Your request code
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        //Setup notification
        //Sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Build notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("My GCM message :X:X")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(sound)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(theBitmap))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
    }
}
