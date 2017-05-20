package com.hack.rohanagarwal94.airtelhack.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by shashank on 2/8/16.
 */
public class Utils {
    private Utils() {
    }

    public static String getUserId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String sha1(final String message) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(message.getBytes("iso-8859-1"), 0, message.length());

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (byte mdbyte : md.digest()) {
            sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        }
        md.reset();
        return sb.toString();
    }

}
