package com.application.presidentapplication.ui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.application.presidentapplication.DatabaseWork.RSSDatabase;
import com.application.presidentapplication.DatabaseWork.RSSDatabaseBuilder;
import com.application.presidentapplication.Network.NetworkStateReader;
import com.application.presidentapplication.R;
import com.application.presidentapplication.Tasks.CacheLoadingTask;
import com.application.presidentapplication.Tasks.RSSFeedControl;



public class NewsFragment extends Fragment {

    private RSSDatabase db;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private SharedPreferences sharedPref;

    private String RSS = "https://www.belta.by/rss/tag/161";
    private static final String APP_PREFERENCES = "preferences";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);

        db = RSSDatabaseBuilder.getInstance(getActivity());
        mRecyclerView = root.findViewById(R.id.post_view);
        mSwipeLayout = root.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPosts();
            }
        });

        sharedPref = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loadPosts();

        return root;
    }


    private void loadPosts(){
        if (NetworkStateReader.getConnectivityStatusString(getActivity()).equals(getResources().getString(R.string.no_internet))){
            mSwipeLayout.setRefreshing(false);
            new CacheLoadingTask(db, getActivity(), mRecyclerView, mSwipeLayout).execute();
        }else{
            new RSSFeedControl(getActivity(), RSS, mSwipeLayout, mRecyclerView, db).execute();
        }
    }
}