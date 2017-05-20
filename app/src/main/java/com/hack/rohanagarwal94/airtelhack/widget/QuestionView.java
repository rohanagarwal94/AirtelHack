package com.hack.rohanagarwal94.airtelhack.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by rohanagarwal94 on 29/6/16.
 */
public class QuestionView {
	private final WindowManager windowManager;
	private final DialogView dialogView;


	public QuestionView(Context context, String destAccount, String amount) {
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		dialogView = new DialogView(context);
		dialogView.setVisibility(View.GONE);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.MATCH_PARENT,
			WindowManager.LayoutParams.MATCH_PARENT,
			WindowManager.LayoutParams.TYPE_PHONE,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 0;

		windowManager.addView(dialogView, params);

	}

	public void onDestroy() {
		windowManager.removeView(dialogView);
	}

	public void setVisibility(boolean visibility) {
		dialogView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        if(isVisibile()){
            dialogView.setOverlayVisibility(true);
        }
	}

	public boolean isVisibile() {
		return dialogView.getVisibility() == View.VISIBLE;
	}

	public void setAccountAndAmount(String account, String amount){
		dialogView.setAccountAndAmount(account, amount);
	}

}

