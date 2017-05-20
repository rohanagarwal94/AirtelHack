package com.hack.rohanagarwal94.airtelhack.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by pkothari on 6/4/16.
 */
public class DefaultPrefSettings {
	private static DefaultPrefSettings ourInstance = new DefaultPrefSettings();
	private SharedPreferences defaultPref;
	private final Object object = new Object();
	public static final String IGNORE_WHATSAPP_HINT = "IGNORE_WHATSAPP_HINT";
	public static final String IGNORE_INSTAGRAM_HINT = "IGNORE_INSTAGRAM_HINT";

	private DefaultPrefSettings() {
	}

	public static DefaultPrefSettings getInstance() {
		return ourInstance;
	}

	public static void init(Context context) {
		ourInstance.defaultPref = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void disableSuggestion() {
		synchronized (object) {
			SharedPreferences.Editor editor = defaultPref.edit();
			editor.putBoolean("EnableSuggestion", false);
			editor.apply();
		}
	}

	public boolean isSuggestionDisabled() {
		synchronized (object) {
			return !defaultPref.getBoolean("EnableSuggestion", true);
		}
	}

	public void setBalance(String balance) {
		synchronized (object) {
			SharedPreferences.Editor editor = defaultPref.edit();
			editor.putString("balance", balance);
			editor.apply();
		}
	}

	public String getBalance() {
		synchronized (object) {
			return defaultPref.getString("balance", null);
		}
	}

	public boolean isFirstTime() {
		boolean firstTime;
		synchronized (object) {
			firstTime = !defaultPref.contains("SuggestionUsed");
		}

		synchronized (object) {
			SharedPreferences.Editor editor = defaultPref.edit();
			editor.putBoolean("SuggestionUsed", true);
			editor.apply();
		}
		return firstTime;
	}

    public void setVisibleDate(Date d1){
        synchronized (object){
            SharedPreferences.Editor editor=defaultPref.edit();
            editor.putLong("VisibilityDate", d1.getTime());
            editor.apply();
        }
    }

    public long getVisibleDate(){
        synchronized (object){
            return defaultPref.getLong("VisibilityDate",0);
        }
    }

    public void setShareTutImageVisibility(){
        synchronized (object){
            SharedPreferences.Editor editor=defaultPref.edit();
            editor.putBoolean("ShareTutImageVisibility",true);
            editor.apply();
        }
    }

    public boolean getShareTutImageVisibility(){
        synchronized (object){
            return defaultPref.getBoolean("ShareTutImageVisibility",false);
        }
    }


	public boolean get(String prefString) {
		synchronized (object) {
			return defaultPref.contains(prefString);
		}
	}

	public void set(String prefString) {
		synchronized (object) {
			SharedPreferences.Editor editor = defaultPref.edit();
			editor.putBoolean(prefString, true);
			editor.apply();
		}
	}
}
