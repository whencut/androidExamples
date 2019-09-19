package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Venkat Vinay on 6/14/2016.
 */
public class ImagesActivity extends AppCompatActivity {
      String imgurl[];
     GridView gv_sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        submitDataToServer();
    }
    ProgressDialog pb;
    private void submitDataToServer() {
        pb = new ProgressDialog(ImagesActivity.this);
        pb.setMessage("Loading...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        //String url = "http://whencutwini.coolpage.biz/practise/getimagesurl.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.IMAGES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        parseFeedbackdData(response);
                        //Toast.makeText(ImagesActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Toast.makeText(ImagesActivity.this, "Server Down.", Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("imgurl", "");
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
            imgurl=new String[jArray.length()];

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                imgurl[i]=jObj.getString("thumb_url");

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
        gv_sample= (GridView) findViewById(R.id.gv_sample);

        gv_sample.setAdapter(new FeedbackAdapter());

    }
    private class FeedbackAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imgurl.length;
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
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=  li.inflate(R.layout.row_gallery1, null);
            }
            CardView cv= (CardView) convertView.findViewById(R.id.cv1);
            ImageView iv_profile=(ImageView) convertView.findViewById(R.id.iv_profile1);
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
            Glide.with(getApplicationContext()).load(imgurl[position]).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(iv_profile);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(ImagesActivity.this,ViewPagerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("position",position+"");
                    startActivity(intent);
                    overridePendingTransition(0,0); //0 for no animation
                }
            });

            return convertView;
        }
    }
}



