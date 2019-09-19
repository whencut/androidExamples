package neoit.cpimpadayatra.views;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import neoit.cpimpadayatra.R;

/**
 * Created by salimatti on 7/30/2016.
 */
public class NewsdetailsActivity extends AppCompatActivity {
    ImageView iv_news1;
    TextView tv_news1;
    LinearLayout llShare;
    ImageButton ib_share_fb,ib_share_whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        AdView adView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        AdRequest adRequest = new AdRequest.Builder().build();
        if (adView != null) {
            adView.loadAd(adRequest);
        }
        getSupportActionBar().setTitle("News Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_news1= (ImageView) findViewById(R.id.iv_news1);
        tv_news1= (TextView) findViewById(R.id.tv_news1);
        ib_share_fb= (ImageButton) findViewById(R.id.ib_share_fb);
        ib_share_whatsapp= (ImageButton) findViewById(R.id.ib_share_whatsapp);
        ib_share_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.facebook.katana");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("news"));
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"Whatsapp have not been installed.",Toast.LENGTH_LONG).show();
                }
            }
        });
        ib_share_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("news"));
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),"Whatsapp have not been installed.",Toast.LENGTH_LONG).show();
                }
            }
        });

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress4);

        Glide.with(getApplicationContext()).load(getIntent().getExtras().getString("imgurl")).listener(new RequestListener<String, GlideDrawable>() {
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

        tv_news1.setText(getIntent().getExtras().getString("news"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {

            fab.setImageResource(R.drawable.ic_share);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = getIntent().getExtras().getString("news");
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
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
