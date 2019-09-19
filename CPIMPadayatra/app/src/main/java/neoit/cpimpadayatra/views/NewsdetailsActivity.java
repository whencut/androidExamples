package neoit.cpimpadayatra.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import neoit.cpimpadayatra.R;

/**
 * Created by salimatti on 7/30/2016.
 */
public class NewsdetailsActivity extends AppCompatActivity {
    ImageView iv_news1;
    TextView tv_news1,tv_head;
CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iv_news1= (ImageView) findViewById(R.id.iv_news1);
        tv_news1= (TextView) findViewById(R.id.tv_news1);
        tv_head= (TextView) findViewById(R.id.tv_head);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress4);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
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

        /*Glide.with(this).load(getIntent().getExtras().getString("imgurl")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    collapsingToolbarLayout.setBackground(drawable);
                }
            }
        });*/
        tv_news1.setText(getIntent().getExtras().getString("news"));
        tv_head.setText(getIntent().getExtras().getString("title") + ":-");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {

            fab.setImageResource(R.drawable.ic_share);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = getIntent().getExtras().getString("news");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getIntent().getExtras().getString("title"));
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
        }
    }
}
