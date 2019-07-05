package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    EditText etNewTweet;
    TextView tvCharCount;
    TwitterClient twitterClient;
    private TextWatcher mTextEditorWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        //Toast.makeText(this, "Cheers", Toast.LENGTH_LONG).show();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setIcon(R.drawable.ic_twitter_logo_whiteonimage);

        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        twitterClient = new TwitterClient(this);

        mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvCharCount.setText(String.valueOf(s.length()) + "/280");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                tvCharCount.setText(String.valueOf(s.length()) + "/280");
            }

            public void afterTextChanged(Editable s) {
                tvCharCount.setText(String.valueOf(s.length()) + "/280");
            }
        };

        etNewTweet.addTextChangedListener(mTextEditorWatcher);

    }

    public void onCompose(View v) {
        String message = etNewTweet.getText().toString();
        twitterClient.sendTweet(message, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("TwitterClient", response.toString());
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent timelineData = new Intent(ComposeActivity.this, TimelineActivity.class);
                    timelineData.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    timelineData.putExtra(User.class.getSimpleName(), Parcels.wrap(tweet.user));

                    setResult(RESULT_OK, timelineData);
                    finish();
                    //startActivity(timeline);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
