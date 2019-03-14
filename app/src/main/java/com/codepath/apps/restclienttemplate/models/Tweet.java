package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

    public String tweetBody;
    public long userID;
    public String createdScreenAt;
    public User user;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.tweetBody = jsonObject.getString("text");
        tweet.userID = jsonObject.getLong("id");
        tweet.createdScreenAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        return  tweet;

    }
    public String getFormattedTimestamp(){

        return TimeFormatter.getTimeDifference(createdScreenAt);
                
    }

}
