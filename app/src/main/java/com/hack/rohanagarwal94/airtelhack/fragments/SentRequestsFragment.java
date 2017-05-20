package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.hack.rohanagarwal94.airtelhack.model.User;
import com.hack.rohanagarwal94.airtelhack.util.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by rohanagarwal94 on 20/5/17.
 */

public class SentRequestsFragment extends Fragment {
    private List<Loan> loans = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    TextView noSessionsView;
    PostsAdapter tracksListAdapter;
    View windowFrame;
    FirebaseDatabase database;
    DatabaseReference myRef;

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
        loans = new ArrayList<>();
        tracksListAdapter = new PostsAdapter(getActivity(), loans);
        recyclerView.setAdapter(tracksListAdapter);

        database = FirebaseDatabase.getInstance();
        PrefManager manager = new PrefManager(getActivity());
        myRef = database.getReference(manager.getNameAndNumber()[1]+"/sendLoans");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Loan loan=data.getValue(Loan.class);
                    Log.i(TAG,loan.getName());
                    loans.add(loan);
                }
                Log.i(TAG,loans.size()+"+");
                tracksListAdapter.notifyDataSetChanged();
                if(loans.size()!=0){
                    handleVisibility();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "onCancelled", databaseError.toException());
                // ...
            }
        };

        myRef.addValueEventListener(postListener);

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