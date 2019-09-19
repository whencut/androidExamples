package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

public class VideosActivity extends AppCompatActivity {

    GridView gv_sample;
    String[] img_url,category,cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        submitDataToServer();
    }

    ProgressDialog pb;
    private void submitDataToServer() {
        pb = new ProgressDialog(VideosActivity.this);
        pb.setMessage("Loading...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);

       // String url = "http://whencutwini.coolpage.biz/practise/youtubeimages.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.VIDEOS_PLAYLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        parseFeedbackdData(response);
                        //Toast.makeText(VideosActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Toast.makeText(VideosActivity.this, "Server Down.", Toast.LENGTH_LONG).show();
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
            img_url=new String[jArray.length()];
            category=new String[jArray.length()];
            cid=new String[jArray.length()];

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                img_url[i]=jObj.getString("image_url");
                category[i]=jObj.getString("category_name");

                cid[i]=jObj.getString("cid");
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
        gv_sample = (GridView) findViewById(R.id.gv_sample);

        gv_sample.setAdapter(new FeedbackAdapter());
       /* gv_sample.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "You Clicked at ", Toast.LENGTH_SHORT).show();

            }
        });
*/
    }
    private class FeedbackAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return img_url.length;
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
                LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView=  li.inflate(R.layout.row_gallery, null);
            }

            ImageView iv_profile=(ImageView) convertView.findViewById(R.id.iv_profile);
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
            Glide.with(getApplicationContext()).load("https://i.ytimg.com/vi/"+img_url[position]+"/mqdefault.jpg").listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model,
                                               Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(iv_profile);
            TextView tv_details= (TextView) convertView.findViewById(R.id.tv_details);
            tv_details.setText("category:- "+category[position]);
            CardView cv=(CardView) convertView.findViewById(R.id.cv);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),VideosInCategoryActivity.class);
                    intent.putExtra("cat",cid[position]+"");
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}



