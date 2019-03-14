package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private Context context;
    private List<Tweet> tweets;
    //Pass in the context of list of tweets


    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row inflate the layout design
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, viewGroup, false);
        return new ViewHolder(view);
    }


    //bind the values based on the position of the elements
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Tweet tweet = tweets.get(position);
         viewHolder.tvBody.setText(tweet.tweetBody);
         viewHolder.tvScreenName.setText(tweet.user.screenName);
         viewHolder.tvTimer.setText(tweet.getFormattedTimestamp());//timer TextView
        Glide.with(context).load(tweet.user.profileImageUrl).into(viewHolder.ivProfileImage);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    // Clean all elements of the recycler

    public void clear() {

        tweets.clear();

        notifyDataSetChanged();

    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {

        tweets.addAll(tweetList);

        notifyDataSetChanged();

    }


    //Define the viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{

            public ImageView ivProfileImage;
            public TextView  tvScreenName;
            public TextView  tvBody;
            public TextView  tvTimer;

        public ViewHolder(View itemView) {
            super(itemView);
          ivProfileImage= itemView.findViewById(R.id.ivProfileImage);
          tvScreenName= itemView.findViewById(R.id.tvScreenName);
          tvBody = itemView.findViewById(R.id.tvBody);
          tvTimer = itemView.findViewById(R.id.tvTimer);

        }


    }
}
