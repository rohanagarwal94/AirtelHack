package com.hack.rohanagarwal94.airtelhack.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.config.DefaultPrefSettings;


/**
 * Created by rohanagarwal94 on 6/3/17.
 */
public class FloatingIcon {
	private final Point screenSize;
	private final WindowManager windowManager;
	private final ImageView chatHead;
    private final RelativeLayout chatHeadLayout;
	private final WindowManager.LayoutParams params;
	private final GestureDetector gestureDetector;
	private final String ignorePrefKey;
	private TextView hintTextView;
	private WindowManager.LayoutParams textViewParams;
    private Animation animationBounce;
	private View removeIcon;
	private final WindowManager.LayoutParams removeIconParams;


	private View.OnTouchListener getOnTouchListener() {
		return new View.OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				removeHintView();
				gestureDetector.onTouchEvent(event);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					params.x = (screenSize.x / 2) > params.x ? 0 : (screenSize.x - chatHeadLayout.getWidth());
					windowManager.updateViewLayout(chatHeadLayout, params);
                    setVisibilityRemoveIcon(false);
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX
						+ (int) (event.getRawX() - initialTouchX);
					params.y = initialY
						+ (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(
                            chatHeadLayout, params);
                    checkRemoveVisibility(params.y);
					return true;
				}
				return false;
			}
		};
	}

	private void removeHintView() {
		if (hintTextView != null) {
			hintTextView.setVisibility(View.GONE);
//			hintTextView.setOnTouchListener(null);
//			windowManager.removeView(hintTextView);
//			hintTextView = null;
		}
	}

	public FloatingIcon(Context context, GestureDetector.OnGestureListener gestureListener, String ignorePrefKey) {
		this.ignorePrefKey = ignorePrefKey;
		boolean shouldShowHintOnce = !DefaultPrefSettings.getInstance().get(ignorePrefKey);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		screenSize = new Point();
		display.getSize(screenSize);

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        chatHeadLayout=(RelativeLayout)inflater.inflate(R.layout.chat_head_layout, null);
        chatHeadLayout.setVisibility(View.GONE);
        chatHead=(ImageView) chatHeadLayout.findViewById(R.id.chat_head);
		removeIcon=inflater.inflate(R.layout.remove_icon, null);


        animationBounce=AnimationUtils.loadAnimation(context, R.anim.bounce);
        animationBounce.setAnimationListener(animationListener);


		params = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.TYPE_PHONE,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
			PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = screenSize.y / 4;


		//this code is for dragging the chat head
		chatHeadLayout.setOnTouchListener(getOnTouchListener());
		windowManager.addView(chatHeadLayout, params);

		removeIconParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		removeIconParams.gravity = Gravity.BOTTOM;
		removeIconParams.x =0;
		removeIconParams.y = 0;
		removeIcon.setVisibility(View.GONE);
		windowManager.addView(removeIcon, removeIconParams);

		gestureDetector = new GestureDetector(context, gestureListener);

		String balance = DefaultPrefSettings.getInstance().getBalance();
        hintTextView = (TextView) View.inflate(context, R.layout.floating_button_hint_text, null);
        hintTextView.setText(String.format(context.getString(R.string.whatsapp_hint_text_first_time), balance));
        hintTextView.setVisibility(View.GONE);
        hintTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                removeHintView();
                return false;
            }
        });

        textViewParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

        textViewParams.gravity = Gravity.TOP | Gravity.LEFT;
        textViewParams.x = 25;
        textViewParams.y = (screenSize.y / 4);

        windowManager.addView(hintTextView, params);
	}



	public void onDestroy() {
		if (chatHeadLayout != null) {
			windowManager.removeView(chatHeadLayout);
		}
	}

	public void setHintText(CharSequence text) {
		if (hintTextView != null) {
			hintTextView.setText(text);
		}
	}

	public void setVisibility(boolean visible) {
		chatHeadLayout.setVisibility(visible ? View.VISIBLE : View.GONE);

		if (hintTextView != null) {
			if (visible) {
                params.x = 0;
                params.y = screenSize.y / 4;
                windowManager.updateViewLayout(chatHeadLayout, params);
				textViewParams.y = params.y - (hintTextView.getHeight()>55?hintTextView.getHeight():55);
				windowManager.updateViewLayout(hintTextView, textViewParams);
				hintTextView.setVisibility(View.VISIBLE);
				DefaultPrefSettings.getInstance().set(ignorePrefKey);
			}
			else if (hintTextView.getVisibility() == View.VISIBLE) {
				removeHintView();
			}
		}
		if(isVisibile()){
            params.x = 0;
            params.y = screenSize.y / 4;
            windowManager.updateViewLayout(chatHeadLayout, params);
            chatHead.startAnimation(animationBounce);
        }else{
            chatHead.clearAnimation();
        }
	}

    private void checkRemoveVisibility(int chatHeadY){
        setVisibilityRemoveIcon(true);
        if(chatHeadY>=(screenSize.y-removeIcon.getHeight())){
            setVisibility(false);
        }
    }

    private void setVisibilityRemoveIcon(boolean visibility){
        if(visibility){
            removeIcon.setVisibility(View.VISIBLE);
        }else{
            removeIcon.setVisibility(View.GONE);
        }
    }

	public boolean isVisibile() {
		return chatHeadLayout.getVisibility() == View.VISIBLE;
	}

    Animation.AnimationListener animationListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

}
