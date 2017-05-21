package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.hack.rohanagarwal94.airtelhack.util.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohanagarwal94 on 20/5/17.
 */

public class ReceivedRequestsFragment extends Fragment {

    private List<Loan> loans;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    TextView noSessionsView;
    PostsAdapter tracksListAdapter;
    View windowFrame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.received_requests_fragment, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.tracks_swipe_refresh);
        recyclerView = (RecyclerView) v.findViewById(R.id.list_tracks);
        noSessionsView = (TextView) v.findViewById(R.id.txt_no_tracks);
        windowFrame = v.findViewById(R.id.tracks_frame);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        tracksListAdapter = new PostsAdapter(getActivity(), loans);
        recyclerView.setAdapter(tracksListAdapter);

        PrefManager manager=new PrefManager(getActivity());

        for(int i=1;i<=manager.getReqQ();i++){

        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Remove listeners to fix memory leak
        if(swipeRefreshLayout != null) swipeRefreshLayout.setOnRefreshListener(null);
    }

    public void handleVisibility() {
            noSessionsView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
    }
}