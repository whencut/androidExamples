package neoit.cpimpadayatra.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import neoit.cpimpadayatra.*;

public class PushdetailsActivity extends AppCompatActivity {
    ImageView iv_news1;
    TextView tv_news1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        AdView adView = (AdView) findViewById(R.id.adView);
       // AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        if (adView != null) {
            adView.loadAd(adRequest);
        }
        getSupportActionBar().setTitle("News Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_news1= (ImageView) findViewById(R.id.iv_news1);
        tv_news1= (TextView) findViewById(R.id.tv_news1);

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress4);

        Glide.with(getApplicationContext()).load(getIntent().getExtras().getString("url")).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                return false;
            }
        }).into(iv_news1);

        tv_news1.setText(getIntent().getExtras().getString("msg"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {

            fab.setImageResource(R.drawable.ic_share);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = getIntent().getExtras().getString("msg");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getIntent().getExtras().getString("title"));
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("title")+" :- \n"+shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
