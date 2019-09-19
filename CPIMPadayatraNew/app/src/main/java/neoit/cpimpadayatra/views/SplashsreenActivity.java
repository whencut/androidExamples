package neoit.cpimpadayatra.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;

public class SplashsreenActivity extends Activity{
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    // please enter your sender id
    String SENDER_ID = "783325208276";//23005536758
    Context context;
    String regid;
    static final String TAG = "GCMDemo";
    GoogleCloudMessaging gcm;
    ConnectionDetector cd;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        cd=new ConnectionDetector(this);
        if(cd.isConnectingToInternet()){
            if(checkPlayServices()){
                gcm = GoogleCloudMessaging.getInstance(this);
                regid = getRegistrationId(context);
                if(regid.isEmpty()){
                    new RegisterBackground().execute();
                }

            }
        }

        final int ScreenDisplay = 3000;
        Thread t1=new Thread(){
            int wait1=0;
            public void run(){
                try{
                    while(wait1<=ScreenDisplay )
                    {
                        sleep(100);
                        wait1+=100;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    startActivity(new Intent(SplashsreenActivity.this,MainActivity.class));
                    finish();

                }
            }
        };
        t1.start();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(cd.isConnectingToInternet()){
            checkPlayServices();
        }else{
            Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    @SuppressLint("NewApi")
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {

        return getSharedPreferences(SplashsreenActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    class RegisterBackground extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(SENDER_ID);
                msg = "Dvice registered, registration ID=" + regid;

                sendRegistrationIdToBackendVolley();
                //AmazonSNS(regid);
                // Persist the regID - no need to register again.
                storeRegistrationId(context, regid);
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }
        private String getDeviceID(){
            String identifier = null;
            try {
                TelephonyManager tm = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null)
                    identifier = tm.getDeviceId();
                if (identifier == null || identifier .length() == 0)
                    identifier = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);
                return identifier;
            } catch (Exception e) {
                return "UnKnown";
            }
        }
        @Override
        protected void onPostExecute(String msg) {
            Toast.makeText(getApplicationContext(),""+regid,Toast.LENGTH_SHORT).show();
        }
        private void sendRegistrationIdToBackendVolley() {
            //

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GCM_REG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SplashsreenActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SplashsreenActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("regid",regid);
                    params.put("device_id",getDeviceID());

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(SplashsreenActivity.this);
            requestQueue.add(stringRequest);
        }

        private void storeRegistrationId(Context context, String regId) {
            final SharedPreferences prefs = getGCMPreferences(context);
            int appVersion = getAppVersion(context);
            Log.i(TAG, "Saving regId on app version " + appVersion);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PROPERTY_REG_ID, regId);
            editor.putInt(PROPERTY_APP_VERSION, appVersion);
            editor.commit();
        }
    }


}
