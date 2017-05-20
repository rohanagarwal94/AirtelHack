package com.hack.rohanagarwal94.airtelhack.config;

import com.hack.rohanagarwal94.airtelhack.R;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String ACCESS_TOKEN = "7191a40d3094";
	public static final String CLIENT_ID = "rohanagarwal94@yahoo.com.sg";
	public static final String ACCESS_CODE = "SBB42OF0";
    public static final String SOURCE_ACC = "4444777755550089";
    public static final String DEST_ACC1 = "4444777755550090";
    public static final String DEST_ACC2 = "4444777755550091";
    public static final String DEST_ACC3 = "4444777755550092";
    public static final String BASE_URL = "https://retailbanking.mybluemix.net/banking/icicibank/";
    public static final int MOBILE_BILL = 1;
    public static final int ELECTRICITY_BILL = 3;
    public static final int GAS_BILL = 2;
    public static final Map<Integer, Integer> imageDrawable = new HashMap<Integer, Integer>() {{
        put(1, R.drawable.smartphone);
        put(2, R.drawable.fire);
        put(0, R.drawable.flash);
    }};

    private Constants() {
	}
}
