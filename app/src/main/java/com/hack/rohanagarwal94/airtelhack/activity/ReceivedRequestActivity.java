package com.hack.rohanagarwal94.airtelhack.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.PrefManager;
import com.hack.rohanagarwal94.airtelhack.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

/**
 * Created by rohanagarwal94 on 21/5/17.
 */

public class ReceivedRequestActivity extends AppCompatActivity {

    private HoloCircleSeekBar seekBar;
    private String names,titles,others;
    private int amount;
    private Button pay;
    private TextView name, title, other;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_request_fragment);
        seekBar = (HoloCircleSeekBar)findViewById(R.id.picker);
        name = (TextView)findViewById(R.id.name1);
        title = (TextView)findViewById(R.id.title1);
        other = (TextView)findViewById(R.id.other1);
        pay = (Button)findViewById(R.id.finance);
        seekBar.getValue();
        seekBar.setMax(amount);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getApplicationContext());
            }
        });
        name.setText(names);
        title.setText(titles);
        other.setText(others);
    }
}