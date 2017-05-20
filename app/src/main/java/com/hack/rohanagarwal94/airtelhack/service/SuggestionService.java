package com.hack.rohanagarwal94.airtelhack.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.hack.rohanagarwal94.airtelhack.api.ClientBuilder;
import com.hack.rohanagarwal94.airtelhack.config.Constants;
import com.hack.rohanagarwal94.airtelhack.config.DefaultPrefSettings;
import com.hack.rohanagarwal94.airtelhack.model.BalanceEnquiryResponse;
import com.hack.rohanagarwal94.airtelhack.model.SuggestionViewManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionService extends AccessibilityService {
    private SuggestionViewManager suggestionViewManager;
    public static boolean requestAccessibilityPermission = false;
    public static final String PERMISSION_GRANTED = "com.hack.rohanagarwal94.airtelhack.PERMISSION_GRANTED";

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

	@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
	public void onAccessibilityEvent(AccessibilityEvent event) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
			return;
		}

        if (suggestionViewManager == null) {
            suggestionViewManager = new SuggestionViewManager(this);
        }

        if (requestAccessibilityPermission) {
            requestAccessibilityPermission = false;
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PERMISSION_GRANTED));
            return;
        }

        if(("com.whatsapp".equals(event.getPackageName()))&&(event.getEventType()==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || event.getEventType()==AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)){
            AccessibilityNodeInfo nodeInfo=getRootInActiveWindow();
            suggestionViewManager.getAllChildNode(nodeInfo);
        }


        if (DefaultPrefSettings.getInstance().isSuggestionDisabled()) {
			return;
		}

//
//        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//            //whatsapp
//            suggestionViewManager
//                    .windowStateChanged(event.getText() != null && event.getText().toString().equalsIgnoreCase("Keyboard"),
//                            event.getPackageName() != null && event.getPackageName().equals("com.whatsapp"),
//                            event.getPackageName() != null && event.getPackageName().length() == 0);
//
//        }

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            String eventText = getEventText(event);
            suggestionViewManager.onTextChanged(eventText);
            //TODO temp solution, fix permanently with replacing with root node view
            AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
            suggestionViewManager.editTextViewNode = record.getSource();
        }
        else {
            AccessibilityNodeInfo source = event.getSource();
            if (source != null) {
                suggestionViewManager.event(event);
                source.recycle();
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        DefaultPrefSettings.init(this);
        //isAddEnabled=DefaultPrefSettings.getInstance().getEarnEnabled();
        //isRunning=true;

        new ClientBuilder().getIciciApi().balanceEnquiry(Constants.CLIENT_ID, Constants.ACCESS_TOKEN, Constants.SOURCE_ACC).enqueue(new Callback<List<BalanceEnquiryResponse>>() {
            @Override
            public void onResponse(Call<List<BalanceEnquiryResponse>> call, Response<List<BalanceEnquiryResponse>> response) {
                String balance = response.body().get(1).getBalance();
                DefaultPrefSettings.getInstance().setBalance(balance);
            }

            @Override
            public void onFailure(Call<List<BalanceEnquiryResponse>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "failed to make the call", Toast.LENGTH_LONG).show();
            }
        });
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
            return;
        }
        if (requestAccessibilityPermission) {
            requestAccessibilityPermission = false;
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PERMISSION_GRANTED));
        }

    }


    @Override
    public void onDestroy() {
        //isRunning=false;
        super.onDestroy();
        if (suggestionViewManager != null) {
            suggestionViewManager.onDestroy();
        }
    }
}