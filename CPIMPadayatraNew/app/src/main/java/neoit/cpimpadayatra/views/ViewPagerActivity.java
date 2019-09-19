package neoit.cpimpadayatra.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import neoit.cpimpadayatra.anims.CubeOutTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewPagerActivity extends AppCompatActivity {
    //String data[]={"Data -1","Data -2","Data -3","Data -4","Data -5"};
    String imgurl[];
    ViewPager pager;
    static int position1,size;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        AdView adView = (AdView) findViewById(R.id.adView);
       // AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        pager=(ViewPager)findViewById(R.id.pager1);
        //pager.setOffscreenPageLimit(3);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle("Gallery     [ "+(position+1)+" / "+size+"]");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        position1= Integer.parseInt(getIntent().getExtras().getString("position"));
        size= Integer.parseInt(getIntent().getExtras().getString("size"));
        //Toast.makeText(getApplicationContext(),""+position1,Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Gallery     [ "+position1+" / "+size+"]");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        submitDataToServer();
    }

    ProgressDialog pb;
    private void submitDataToServer() {
        pb = new ProgressDialog(ViewPagerActivity.this);
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
                       //Toast.makeText(ViewPagerActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Toast.makeText(ViewPagerActivity.this, "Server Down.", Toast.LENGTH_LONG).show();
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
                imgurl[i]=jObj.getString("imgurl");

            }
            if(jArray.length()>0){

                pager.setAdapter(new NewsAdapter());
                pager.setCurrentItem(position1);
                pager.setPageTransformer(true,new CubeOutTransformer());
            }else{
                Toast.makeText(getApplicationContext(),"Nodata found", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private class NewsAdapter extends PagerAdapter {




        @Override
        public int getCount() {
            return imgurl.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = (LayoutInflater) ViewPagerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.row_viewpager, container, false);

            ImageView iv=(ImageView) itemView.findViewById(R.id.iv_rowviewpager);
            final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progress1);
            Glide.with(getApplicationContext()).load(Constants.BASE_URL+imgurl[position]).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(iv);

            ((ViewPager) container).addView(itemView);

            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove row_view_pager.xml from ViewPager
            ((ViewPager) container).removeView((LinearLayout) object);
            //Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
        }




    }
    public void onBackPressed(){

        finish();
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
