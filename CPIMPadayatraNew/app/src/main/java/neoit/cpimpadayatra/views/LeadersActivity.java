package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;
import neoit.cpimpadayatra.adapters.LeadersAdapter;

/**
 * Created by salimatti on 8/1/2016.
 */
public class LeadersActivity extends AppCompatActivity {
    String[] names,district,phone_number;
    RecyclerView recyclerview;
    ArrayList<HashMap<String,String>> memberList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders);
      AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        if (adView != null) {
            adView.loadAd(adRequest);
        }


        getSupportActionBar().setTitle("Leaders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        submitDataToServer();
    }

    ProgressDialog pb;

    private void submitDataToServer() {
        pb = new ProgressDialog(LeadersActivity.this);
        pb.setMessage("Loading...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CONTACT_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        parseFeedbackdData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Toast.makeText(LeadersActivity.this, "Server Down."+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(stringRequest);
    }
    private void parseFeedbackdData(String response){
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");//name,phone_number,emailid,village,mandal,district
            HashMap<String,String> hm;
            memberList = new ArrayList<HashMap<String,String>>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                hm=new HashMap<String,String>();
                hm.put("name",jObj.getString("name"));
                hm.put("phone_number",jObj.getString("phone_number"));
                hm.put("emailid",jObj.getString("emailid"));
                hm.put("village",jObj.getString("village"));
                hm.put("mandal",jObj.getString("mandal"));
                hm.put("district",jObj.getString("district"));
                memberList.add(hm);
            }
            if(jArray.length()>0){
                displayFeedbackDetails();
            }else{
                Toast.makeText(getApplicationContext(),"Nodata found", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void displayFeedbackDetails(){
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LeadersActivity.this);
        recyclerview.setLayoutManager(layoutManager);
        LeadersAdapter adapter=new LeadersAdapter(memberList,this);
        recyclerview.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}