package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.HashMap;
import java.util.Map;

import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;
import neoit.cpimpadayatra.gcm.GCMRegistrationIntentService;

public class SplashsreenActivity extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver=null;
    SharedPreferences sharedpreferences;
    String gcm_fg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         sharedpreferences = getSharedPreferences("cpim_padayatra", Context.MODE_PRIVATE);
         gcm_fg=sharedpreferences.getString("cpim_gcm", null);
         if(gcm_fg==null){
             GCMRegistration();
         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(gcm_fg==null) {
            if(mRegistrationBroadcastReceiver!=null) {
                LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                        new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
                LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                        new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
                startActivity(new Intent(SplashsreenActivity.this, MainActivity.class));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gcm_fg==null) {
            if(mRegistrationBroadcastReceiver!=null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        }
    }

    private void submitDataToServer(final String token) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GCM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", token);
                params.put("Device", "---");
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void GCMRegistration(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check type of intent filter
                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    //Registration success
                    String token = intent.getStringExtra("token");
                    submitDataToServer(token);
                } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    //Registration error
                    Toast.makeText(getApplicationContext(), "GCM registration error!!!", Toast.LENGTH_LONG).show();
                } else {
                    //Tobe define
                }
            }
        };

        //Check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode) {
            //Check type of error
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                //So notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }
        } else {
            //Start service
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
    }
}
