package com.application.presidentapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.presidentapplication.Activities.RSSPostActivity;
import com.application.presidentapplication.Models.RSSPost;
import com.application.presidentapplication.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.RSSPostViewHolder> {
    private final Activity myContext;
    private final ArrayList<RSSPost> posts;

    static public class RSSPostViewHolder extends RecyclerView.ViewHolder{
        final TextView postTitleView;
        final TextView postContentView;

        final View rssFeedView;

        RSSPostViewHolder(View v) {
            super(v);
            rssFeedView = v;
            postTitleView = v.findViewById(R.id.postTitleLabel);
            postContentView = v.findViewById(R.id.postContent);
        }
    }

    public PostAdapter(Context context,  ArrayList<RSSPost> postList) {
        myContext = (Activity) context;
        posts = postList;
    }

    @NonNull
    @Override
    public RSSPostViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        return new RSSPostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RSSPostViewHolder holder, int position) {
        final RSSPost rssFeedModel = posts.get(position);
        holder.postTitleView.setText(rssFeedModel.getTitle());
        holder.postContentView.setText(rssFeedModel.getContent());
        holder.rssFeedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(myContext, RSSPostActivity.class);
                i.putExtra("link", rssFeedModel.getLink());
                i.putExtra("position", posts.indexOf(rssFeedModel));
                myContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
