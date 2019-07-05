package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvUsername;
    TextView tvCreatedAt;
    TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setIcon(R.drawable.ic_twitter_logo_whiteonimage);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvCreatedAt = (TextView) findViewById(R.id.tvCreatedAt);
        tvBody = (TextView) findViewById(R.id.tvBody);

        loadTweet();
    }

    public void loadTweet() {
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tweet.user = Parcels.unwrap(getIntent().getParcelableExtra(User.class.getSimpleName()));

        tvBody.setText(tweet.body);
        tvCreatedAt.setText(tweet.getCreatedAt());
        tvUsername.setText(tweet.user.name);

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 15, 0))
                .into(ivProfileImage);
    }
}
