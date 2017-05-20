package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hack.rohanagarwal94.airtelhack.R;

/**
 * Created by rohanagarwal94 on 20/5/17.
 */

public class ReceivedRequestsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.received_requests_fragment, container, false);
        return v;
    }
}