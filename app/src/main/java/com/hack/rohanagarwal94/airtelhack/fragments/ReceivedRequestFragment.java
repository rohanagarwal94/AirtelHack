package com.hack.rohanagarwal94.airtelhack.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

/**
 * Created by rohanagarwal94 on 21/5/17.
 */

public class ReceivedRequestFragment extends Fragment {

    private HoloCircleSeekBar seekBar;
    private String names,titles,others;
    private TextView name, title, other;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.receive_request_fragment, container, false);
        seekBar = (HoloCircleSeekBar) v.findViewById(R.id.picker);
        name = (TextView)v.findViewById(R.id.)
        seekBar.getValue();
        return v;
    }
}