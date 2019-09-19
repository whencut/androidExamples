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
 * Created by salimatti on 7/30/2016.
 */
public class NewsActivity extends AppCompatActivity {

    String news[],imgurl[],thumburl[],title[];
    ListView lv_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        AdView adView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        submitDataToServer();

        getSupportActionBar().setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    ProgressDialog pb;
    private void submitDataToServer() {
        pb = new ProgressDialog(NewsActivity.this);
        pb.setMessage("Loading...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.NEWS,
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
                        Toast.makeText(NewsActivity.this, "Server Down."+error, Toast.LENGTH_SHORT).show();
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
            JSONArray jArray = json.getJSONArray("data");
            news=new String[jArray.length()];
            imgurl=new String[jArray.length()];
            title=new String[jArray.length()];
            thumburl=new String[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                news[i]=jObj.getString("description");
                imgurl[i]=jObj.getString("img_url");
                title[i]=jObj.getString("title");
                thumburl[i]=jObj.getString("thumb_url");

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
            return news.length;
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
                convertView=  li.inflate(R.layout.row_news_details, null);
            }
            CardView cv_news= (CardView) convertView.findViewById(R.id.cv_news);
            TextView tv_shortnews= (TextView) convertView.findViewById(R.id.tv_shortnews);
            tv_shortnews.setText(title[position]);
            ImageView iv_news= (ImageView) convertView.findViewById(R.id.iv_news);
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progress2);
            Glide.with(getApplicationContext()).load(thumburl[position]).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(iv_news);

            cv_news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(NewsActivity.this,NewsdetailsActivity.class);
                    intent.putExtra("news",news[position]);
                    intent.putExtra("imgurl",imgurl[position]);
                    intent.putExtra("title",title[position]);
                    startActivity(intent);
                }
            });
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
