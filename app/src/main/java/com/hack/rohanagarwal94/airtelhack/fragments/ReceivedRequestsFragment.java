package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.Loan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohanagarwal94 on 20/5/17.
 */

public class ReceivedRequestsFragment extends Fragment {

    private List<Loan> loans = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    TextView noSessionsView;
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

        return v;
    }
}