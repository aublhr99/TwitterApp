package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet {

    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;

    //deserialize JSON Data
    public static Tweet fromJSON(JSONObject object) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = object.getString("text");
        tweet.uid = object.getLong("id");
        tweet.createdAt = object.getString("created_at");
        tweet.user = User.fromJSON(object.getJSONObject("user"));


        return tweet;
    }

    public Tweet() {}

    public String getCreatedAt() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(createdAt).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
