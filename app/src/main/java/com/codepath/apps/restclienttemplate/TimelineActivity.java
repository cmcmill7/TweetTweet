package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private RecyclerView rvTweets;
    private TweetAdapter adapter;
    private List<Tweet> tweets;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                 // loadNextDataFromApi(page);

            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        // Append the next page of data into the adapter
        // This method probably sends out a network request and appends new data items to your adapter.
       //public void loadNextDataFromApi(int offset) {
            // Send an API request to retrieve appropriate paginated data
            //populateHomeTimeline();
            //  --> Send the request including an offset value (i.e `page`) as a query parameter.

            //  --> Deserialize and construct new model objects from the API response
            //  --> Append the new data objects to the existing set of items inside the array of items
            //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
      // }
        client = TwitterApp.getRestClient(this);
        //find the swipeContainer view
        swipeContainer = findViewById(R.id.swipeContainer);
        //Find the recycler view
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        rvTweets = findViewById(R.id.rvTweets);
        //Initialize list of tweets and from the adapter from the data source
        tweets = new ArrayList<>();
        adapter = new TweetAdapter(this, tweets);
        //Recycler View setup: layout manager and setting  the adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this ));
        rvTweets.setAdapter(adapter);
        populateHomeTimeline();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TwitterClient","content is being refreshed");
                populateHomeTimeline();
            }
        });
    }
    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d( "TwitterClient", response.toString());
                //Iterate through the list of tweets
                List<Tweet> tweetsToAdd = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    try {
                        //Convert each JsonObject into a tweet object
                        JSONObject jsonTweetObject =  response.getJSONObject(i);
                       Tweet tweet = Tweet.fromJson(jsonTweetObject);
                        //Add the tweet into the data source
                        tweetsToAdd.add(tweet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Clearing the existing data from the tweets that are displayed
                adapter.clear();
                //Show the recent tweets
                adapter.addAll(tweetsToAdd);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void  onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e( "TwitterClient", responseString);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d( "TwitterClient", errorResponse.toString());

            }
        });
    }
}
