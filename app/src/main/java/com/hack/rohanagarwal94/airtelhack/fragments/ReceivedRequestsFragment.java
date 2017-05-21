package com.hack.rohanagarwal94.airtelhack.fragments;

import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.activity.ReceivedRequestActivity;
import com.hack.rohanagarwal94.airtelhack.model.Creditor;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.hack.rohanagarwal94.airtelhack.util.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

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
        loans=new ArrayList<>();
        tracksListAdapter = new PostsAdapter(getActivity(), loans);

        final PrefManager manager=new PrefManager(getActivity());
        tracksListAdapter.SetOnItemClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG," gyfgewhj");
                Intent intent=new Intent(getActivity(), ReceivedRequestActivity.class);
                intent.putExtra("key",manager.getKeyAndNumber(position+1)[0]);
                intent.putExtra("number",manager.getKeyAndNumber(position+1)[1]);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(tracksListAdapter);



        for(int i=1;i<=manager.getReqQ();i++){
            loans.clear();
            String[] data=manager.getKeyAndNumber(i);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference(data[1]+"/sendLoans/"+data[0]);

            ValueEventListener postListener = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot data) {
                    Log.i(TAG,data.toString());
                        Loan loan=new Loan();
                        loan.setAmountLeft(Float.parseFloat(data.child("amountLeft").getValue().toString()));
                        loan.setAmountTotal(Float.parseFloat(data.child("amountTotal").getValue().toString()));
                        loan.setName(data.child("name").getValue().toString());
                        loan.setTitle(data.child("title").getValue().toString());
                        loan.setType(Integer.parseInt(data.child("type").getValue().toString()));
                        ArrayList<Creditor> creditors=new ArrayList<>();
                        if(data.hasChild("creditors")){
                            for(DataSnapshot ds:data.child("creditors").getChildren()){
                                Creditor creditor=ds.getValue(Creditor.class);
                                creditors.add(creditor);
                            }
                        }
                        loan.setCreditors(creditors);
                        loans.add(loan);

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

            myRef.addListenerForSingleValueEvent(postListener);

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