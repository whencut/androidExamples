package neoit.cpimpadayatra.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
    TextView tv_map,tv_news,tv_images,tv_videos,tv_upcomingevents,tv_pastevents,tv_Objectives,tv_leaders,tv_signup,tv_suggestions;
    public static String[] imges={"http://goo.gl/sLSPTO","http://goo.gl/Obh1tf","http://bit.ly/2aqNvrF","http://bit.ly/2aGKoAg","http://goo.gl/kp054t"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        if (adView != null) {
            adView.loadAd(adRequest);
        }

        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
        tv_news = (TextView) findViewById(R.id.tv_news);
        tv_images = (TextView) findViewById(R.id.tv_images);
        tv_videos = (TextView) findViewById(R.id.tv_videos);
        tv_upcomingevents = (TextView) findViewById(R.id.tv_upcomingevents);
        tv_pastevents = (TextView) findViewById(R.id.tv_pastevents);
        tv_Objectives = (TextView) findViewById(R.id.tv_Objectives);
        tv_leaders = (TextView) findViewById(R.id.tv_leaders);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        tv_suggestions = (TextView) findViewById(R.id.tv_suggestions);
        tv_map = (TextView) findViewById(R.id.tv_map);
        intro_images.setPageTransformer(true, new ZoomOutTranformer());

        tv_news.setOnClickListener(this);
        tv_images.setOnClickListener(this);
        tv_videos.setOnClickListener(this);
        tv_upcomingevents.setOnClickListener(this);
        tv_pastevents.setOnClickListener(this);
        tv_Objectives.setOnClickListener(this);
        tv_leaders.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        tv_suggestions.setOnClickListener(this);
        tv_map.setOnClickListener(this);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mAdapter = new ViewPagerAdapter(MainActivity.this);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_map:
                Intent intent = new Intent(this, MapsActivity.class);
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
            case R.id.tv_upcomingevents:
                Intent intent1=new Intent(MainActivity.this,EventsActivity.class);
                intent1.putExtra("type","upcoming");
                startActivity(intent1);
                break;
            case R.id.tv_pastevents:
                Intent intent2=new Intent(MainActivity.this,EventsActivity.class);
                intent2.putExtra("type","past");
                startActivity(intent2);
                break;
            case R.id.tv_Objectives:
                startActivity(new Intent(MainActivity.this,ObjectivesActivity.class));
                break;
            case R.id.tv_leaders:
                startActivity(new Intent(MainActivity.this,LeadersActivity.class));
                break;
            case R.id.tv_signup:
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
                break;
            case R.id.tv_suggestions:
                startActivity(new Intent(MainActivity.this,SuggessionsActivity.class));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder al =  new AlertDialog.Builder(this);
al.setIcon(R.mipmap.ic_launcher);
        //al.setInverseBackgroundForced(true);
        al.setMessage("do u want to close this application")
                .setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }else {
                            finish();
                        }
                    }
                });
        AlertDialog ad = al.create();

        ad.setTitle("Padayatra");
        ad.show();
    }
}
