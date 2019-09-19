package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;
import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;

public class PadayatraDetailsActivity extends AppCompatActivity {

    String from_place[],to_place[],km_details[],d_details[];
    ListView lv_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        AdView adView = (AdView) findViewById(R.id.adView);
       // AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        submitDataToServer();

        getSupportActionBar().setTitle("Padayatra Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    ProgressDialog pb;
    private void submitDataToServer() {
        pb = new ProgressDialog(PadayatraDetailsActivity.this);
        pb.setMessage("Loading...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PADAYATRA_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        //Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                        parseFeedbackdData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Toast.makeText(PadayatraDetailsActivity.this, "Server Down."+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void parseFeedbackdData(String response){
        try {
            JSONObject json = new JSONObject(response);
            JSONArray jArray = json.getJSONArray("data");
            from_place=new String[jArray.length()];
            to_place=new String[jArray.length()];
            km_details=new String[jArray.length()];
            d_details=new String[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                from_place[i]=jObj.getString("from_place");
                to_place[i]=jObj.getString("to_place");
                km_details[i]=jObj.getString("km_details");
                d_details[i]=jObj.getString("date");

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
        lv_news=(ListView) findViewById(R.id.lv_news);
        lv_news.setAdapter(new FeedbackAdapter());

        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
    private class FeedbackAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return from_place.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater li = ( LayoutInflater ) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=  li.inflate(R.layout.row_padayatra_details, null);
            }
            CardView cv_news= (CardView) convertView.findViewById(R.id.cv_news);
            TextView tv_from_date= (TextView) convertView.findViewById(R.id.tv_from_date);
            tv_from_date.setText(from_place[position]);
            TextView tv_to_date= (TextView) convertView.findViewById(R.id.tv_to_date);
            tv_to_date.setText(to_place[position]);
            TextView tv_km= (TextView) convertView.findViewById(R.id.tv_km);
            tv_km.setText(km_details[position]);
          TextView tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            tv_date.setText(d_details[position]);
            return convertView;
        }
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
