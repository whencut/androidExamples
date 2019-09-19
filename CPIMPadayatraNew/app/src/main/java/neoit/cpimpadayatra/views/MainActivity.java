package neoit.cpimpadayatra.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import neoit.cpimpadayatra.Constants;
import neoit.cpimpadayatra.R;
import neoit.cpimpadayatra.adapters.ViewPagerAdapter;
import neoit.cpimpadayatra.anims.ZoomOutTranformer;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    int present_img_id=1;
    TextView tv_map,tv_news,tv_images,tv_videos,tv_scroll_news;
    public static String[] imges={"http://goo.gl/sLSPTO","http://goo.gl/Obh1tf","http://bit.ly/2aqNvrF","http://bit.ly/2aGKoAg","http://goo.gl/kp054t"};
    SharedPreferences sharedPreferences;
    ImageButton ivUpcomingEvents,ivPastEvents,ivObjectives,ivLeader,ivGetInTouch,ivFeedback,ivFacebook,ivLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.exit_unit_id));

        AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);
        sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);


        /*AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        if (adView != null) {
            adView.loadAd(adRequest);
        }*/
         AdView adView = (AdView) findViewById(R.id.adView);
        if (adView != null) {
            adView.loadAd(adRequest);
        }

        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        tv_news = (TextView) findViewById(R.id.tv_news);
        tv_images = (TextView) findViewById(R.id.tv_images);
        tv_videos = (TextView) findViewById(R.id.tv_videos);
        tv_map = (TextView) findViewById(R.id.tv_map);

        tv_scroll_news = (TextView) findViewById(R.id.tv_scroll_news);
        tv_scroll_news.setSelected(true);

        ivUpcomingEvents=(ImageButton)findViewById(R.id.ivUpcomingEvents);

        ivPastEvents=(ImageButton)findViewById(R.id.ivPastEvents);
        ivObjectives=(ImageButton)findViewById(R.id.ivObjectives);
        ivLeader=(ImageButton)findViewById(R.id.ivLeader);

        ivGetInTouch=(ImageButton)findViewById(R.id.ivGetInTouch);
        ivFeedback=(ImageButton)findViewById(R.id.ivFeedback);
        ivFacebook=(ImageButton)findViewById(R.id.ivFacebook);
        ivLogin=(ImageButton)findViewById(R.id.ivLogin);

        ivUpcomingEvents.setOnClickListener(this);
        tv_scroll_news.setOnClickListener(this);
        ivPastEvents.setOnClickListener(this);
        ivObjectives.setOnClickListener(this);
        ivLeader.setOnClickListener(this);

        ivGetInTouch.setOnClickListener(this);
        ivFeedback.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivLogin.setOnClickListener(this);

        intro_images.setPageTransformer(true, new ZoomOutTranformer());

        tv_news.setOnClickListener(this);
        tv_images.setOnClickListener(this);
        tv_videos.setOnClickListener(this);

        tv_map.setOnClickListener(this);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mAdapter = new ViewPagerAdapter(MainActivity.this);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        getDashboardDetails();
        setUiPageViewController();
        submitDataToServer();
    }
    String title;
    private void getDashboardDetails(){
        imges[0]=sharedPreferences.getString("img1","");
        imges[1]=sharedPreferences.getString("img2","");
        imges[2]=sharedPreferences.getString("img3","");
        imges[3]=sharedPreferences.getString("img4","");
        imges[4]=sharedPreferences.getString("img5","");
        title=sharedPreferences.getString("title","Loading...");
        tv_scroll_news.setText(title);
    }
    private void submitDataToServer() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DASHBOARD_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                            JSONObject json = new JSONObject(response);
                            JSONArray jArray = json.getJSONArray("data");
                            JSONObject jObj = jArray.getJSONObject(0);

                            prefsEditor.putString("img1", jObj.getString("img_url1"));
                            prefsEditor.putString("img2", jObj.getString("img_url2"));
                            prefsEditor.putString("img3", jObj.getString("img_url3"));
                            prefsEditor.putString("img4", jObj.getString("img_url4"));
                            prefsEditor.putString("img5", jObj.getString("img_url5"));
                            prefsEditor.putString("title", jObj.getString("title"));
                            tv_scroll_news.setText(jObj.getString("title"));

                            imges[0]=jObj.getString("img_url1");
                            imges[1]=jObj.getString("img_url2");
                            imges[2]=jObj.getString("img_url3");
                            imges[3]=jObj.getString("img_url4");
                            imges[4]=jObj.getString("img_url5");

                            prefsEditor.commit();
                        }catch (Exception e){}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(stringRequest);
    }


    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.nonselecteditem_dot, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selecteditem_dot, null));
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        present_img_id=position;
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.nonselecteditem_dot, null));
        }

        dots[position].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selecteditem_dot, null));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    InterstitialAd mInterstitialAd;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_map:
                Intent intent = new Intent(this, PadayatraDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                break;
            case R.id.tv_news:
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                break;
            case R.id.tv_images:
                startActivity(new Intent(MainActivity.this,ImagesActivity.class));
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                break;
            case R.id.tv_videos:
                startActivity(new Intent(MainActivity.this,VideosActivity.class));
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                break;
            case R.id.ivUpcomingEvents:
                Intent intent1=new Intent(MainActivity.this,EventsActivity.class);
                intent1.putExtra("type","upcoming");
                startActivity(intent1);
                break;
            case R.id.tv_scroll_news:
                Intent intent12=new Intent(MainActivity.this,EventsActivity.class);
                intent12.putExtra("type","upcoming");
                startActivity(intent12);
                break;

            case R.id.ivPastEvents:
                /*Intent intent2=new Intent(MainActivity.this,EventsActivity.class);
                intent2.putExtra("type","past");
                startActivity(intent2);*/
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;
            case R.id.ivObjectives:
                startActivity(new Intent(MainActivity.this,ObjectivesActivity.class));
                break;
            case R.id.ivLeader:
                startActivity(new Intent(MainActivity.this,LeadersActivity.class));
                break;
            case R.id.ivGetInTouch:
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
                break;
            case R.id.ivFeedback:
                startActivity(new Intent(MainActivity.this,SuggessionsActivity.class));
                break;
            case R.id.ivFacebook:
                FaceBookPage();
                break;
            case R.id.ivLogin:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showInterstitial();
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            ShareApp();
            return true;
        }
        if (id == R.id.action_login) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void FaceBookPage(){
        String facebookUrl = "https://www.facebook.com/cpim.telangana";
        try {
            int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));;
            }
        } catch (PackageManager.NameNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
        }
    }
    private void ShareApp(){
        try
        {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Mahapadayathra");
            String sAux = "PLaystore Link";
            sAux = sAux + "URL";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        }
        catch(Exception e){}
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
