package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
    TwitterClient twitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        //Toast.makeText(this, "Cheers", Toast.LENGTH_LONG).show();

        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        twitterClient = new TwitterClient(this);
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
