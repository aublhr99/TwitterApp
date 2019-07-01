package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;

    //deserialize JSON Data
    public static Tweet fromJSON(JSONObject object) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = object.getString("body");
        tweet.uid = object.getLong("id");
        tweet.createdAt = object.getString("created_at");
        tweet.user = User.fromJSON(object.getJSONObject("user"));

        return tweet;
    }


}
