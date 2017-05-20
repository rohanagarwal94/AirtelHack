package com.hack.rohanagarwal94.airtelhack.util;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Interceptor;

import java.util.List;

/**
 * Created by pkothari on 4/9/16.
 */
public class StethoUtil {
	public static void install(Context context) {
		Stetho.initialize(Stetho.newInitializerBuilder(context)
			.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
			.build());
	}

	public static void addNetworkInterceptors(List<Interceptor> interceptors) {
		interceptors.add(new StethoInterceptor());
	}
}
