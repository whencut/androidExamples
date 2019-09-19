package neoit.cpimpadayatra.views;

/* http://techlovejump.in/2013/11/android-push-notification-using-google-cloud-messaging-gcm-php-google-play-service-library/
 * techlovejump.in
 * tutorial link
 * 
 *  */

import org.json.JSONObject;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import neoit.cpimpadayatra.R;

public class GcmIntentService extends IntentService{
	Context context;
	public static final int NOTIFICATION_ID = 1;
	public static String MESSAGE = null;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final String TAG = "GCM Demo";
	String msg;
	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		 msg = intent.getExtras().getString("message");

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		
		 if (!extras.isEmpty()) {
			 
			 if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	                sendNotification("Send error: " + extras.toString());
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_DELETED.equals(messageType)) {
	                sendNotification("Deleted messages on server: " +
	                        extras.toString());
	            // If it's a regular GCM message, do some work.
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {

	                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
	                // Post notification of received message.
	                sendNotification(msg);
				 //sendNotification(extras.getString("title"));
	                //CustomNotification(msg);
	                Log.i(TAG, "Received: " + extras.toString());
	            }
	        }
		 GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	JSONObject json;
	Intent mIntent;
	private void sendNotification(String msg) {

		String mTitle="";
		String mMsg="";
		String mUrl="";
		try{
		json = new JSONObject(msg);
		//message = json.getString("name");
			mTitle = json.getString("title");
			mUrl = json.getString("url");
			mMsg= json.getString("msg");

		}catch(Exception e){}
        mNotificationManager = (NotificationManager)
        this.getSystemService(Context.NOTIFICATION_SERVICE);
        mIntent = new Intent(this, PushdetailsActivity.class);
		mIntent.putExtra("msg", mMsg);
        mIntent.putExtra("url", mUrl);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       // Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);
        
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_cast_light)
        .setSound(uri)
        .setContentTitle(mTitle)
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(mMsg))
        
        .setAutoCancel(true)
        .setContentText(mMsg);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cast_light);
        mBuilder.setLargeIcon(bm);
        
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
	
}