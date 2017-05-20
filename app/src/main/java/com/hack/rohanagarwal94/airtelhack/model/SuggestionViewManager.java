package com.hack.rohanagarwal94.airtelhack.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.config.DefaultPrefSettings;
import com.hack.rohanagarwal94.airtelhack.widget.FloatingIcon;
import com.hack.rohanagarwal94.airtelhack.widget.QuestionView;

/**
 * Created by pkothari on 6/3/16.
 */
public class SuggestionViewManager extends GestureDetector.SimpleOnGestureListener {
    private QuestionView questionView;
	private final FloatingIcon floatingIcon;
	private final Context context;
	private Handler handler = new Handler();
	public AccessibilityNodeInfoCompat editTextViewNode;
    private ArrayList<String> hashStringSended;
    private String destAccount;
    private String amount;

	public SuggestionViewManager(Context context) {
		this.context = context;
		floatingIcon = new FloatingIcon(context, this, DefaultPrefSettings.IGNORE_WHATSAPP_HINT);
//		carousel = new Carousel(context, getImageOnClickListener());
        questionView = new QuestionView(context, destAccount, amount);
        hashStringSended=new ArrayList<String>();
//		greetingListAPI.get();
	}

	public boolean onTextChanged(String eventText) {

		String lowercaseEvent = eventText.toLowerCase();


        if(eventText.length()==1){
            hashStringSended.clear();
        }

        String keyword2 = "airtel\\spay\\s[0-9]+\\s[0-9]+";

        String keyword = "airtel pay 20 971149911400";

        keyword = "airtel\\spay\\s\\d+\\s\\d{16}";

        String accoutNoexp = "airtel\\spay\\s\\d+\\s\\d{16,22}";  // assuming account no. is between 16-22 digits

        String pattern="^.*\\b("+keyword+")\\b.*$";
        Pattern r=Pattern.compile(keyword);
        Log.d("pattern",keyword);
        Matcher m=r.matcher(lowercaseEvent);
        if(m.matches()) {
			String balance = DefaultPrefSettings.getInstance().getBalance();
            destAccount = lowercaseEvent.substring(lowercaseEvent.lastIndexOf(' ') + 1);
            amount = lowercaseEvent.substring(10, lowercaseEvent.indexOf(' ', lowercaseEvent.indexOf(' ') + 7));
            Log.d("amount", amount);
            Log.d("destaccount", destAccount);
            questionView.setAccountAndAmount(destAccount, amount);
            Log.d("pattern matched",pattern);
            if (!floatingIcon.isVisibile()) {
                    floatingIcon
                            .setHintText(String.format(context.getString(R.string.whatsapp_hint_text_first_time), balance));
                    floatingIcon.setVisibility(true);
                    return true;
            }
        }


		questionView.setVisibility(false);
        floatingIcon.setVisibility(false);
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
//		greetingListAPI.get();
//		carousel.setVisibility(!carousel.isVisibile());
        floatingIcon.setVisibility(!floatingIcon.isVisibile());
        double balance = Double.parseDouble(DefaultPrefSettings.getInstance().getBalance());
        double payment = Double.parseDouble(amount);
        if(balance > payment)
		    questionView.setVisibility(!questionView.isVisibile());
        else
            Toast.makeText(context,"Insufficient balance", Toast.LENGTH_LONG).show();
		return true;
	}

	public void onDestroy() {
		floatingIcon.onDestroy();
//		carousel.onDestroy();
		questionView.onDestroy();

	}

	public void windowStateChanged(boolean isKeyboardHiddenEvent, boolean isPackageNameWhatsApp,
		boolean isNullPackageName) {

		if (!isNullPackageName) {
			floatingIcon.setVisibility(false);
		}
//		if ((!isPackageNameWhatsApp || isKeyboardHiddenEvent) && !carousel.isLoadingOverlayVisible()) {
////			carousel.setVisibility(false);
//		}
		if (!isPackageNameWhatsApp) {
//			carousel.setVisibility(false);
		}
	}

	private void forceRemoveAllViews() {
		floatingIcon.setVisibility(false);
		questionView.setVisibility(false);
	}

	public void event(AccessibilityEvent event) {
//		if (sendImageState != SendImageState.IDLE && !chooseContactScreen.get()) {
//			return;
//		}
		AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
		AccessibilityNodeInfoCompat source = record.getSource();
		if (source == null) {
			return;
		}

//		try {
//			if (sendImageState == SendImageState.IDLE && source.getClassName().equals("android.widget.FrameLayout")
//				&& source.getChild(1).getChild(0).getText() != null) {
//				for (int index = 0; index < source.getChildCount(); index++) {
//					if (source.getChild(index).getClassName().equals("android.widget.EditText")) {
//						specificContactName = source.getChild(1).getChild(0).getText();
//						return;
//					}
//				}
//			}
//			else if (sendImageState == SendImageState.SEARCH && source.getClassName()
//				.equals("android.widget.FrameLayout")) {
//				if (source.getChild(2).getContentDescription().equals("Search")) {
//					source.getChild(2).performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
//					sendImageState = SendImageState.CLICKED;
//				}
//			}
//			else if (sendImageState == SendImageState.CLICKED && source.getClassName()
//				.equals("android.widget.EditText")) {
//
//
//				Bundle arguments = new Bundle();
//				String specificContactNameWithoutEmoji=nameWithoutEmoji(specificContactName);
//				arguments.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
//						specificContactNameWithoutEmoji);
//				source.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT,arguments);
//				sendImageState = SendImageState.FIND_CONTACT;
//			}
//			else if (sendImageState == SendImageState.FIND_CONTACT) {
//				if (specificContactName != null && source.getClassName().equals("android.widget.ListView")
//					&& source.getChildCount() != 0) {
//
//					//remove emoji before comparison
//					String childName =
//							source.getChild(1).getChild(1).getText().toString().replace("…", "");
//                    String childNameWithoutEmoji=nameWithoutEmoji(childName);
//					String specificContactNameWithoutEmoji =nameWithoutEmoji(specificContactName);
//					if (childNameWithoutEmoji.indexOf(specificContactNameWithoutEmoji) == 0) {
//						//Check whether its group or not and send it.
//						chooseContactScreen.tick(2_000);
//						source.getChild(1).performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
//						sendImageState=SendImageState.CONTACT_CLICKED;
//					}
//				}
//			}
//		}
//		catch (Exception ignore) {
//		}
	}

	public void getAllChildNode(AccessibilityNodeInfo node) {
		if (node == null) {
			return;
		}
		int child = node.getChildCount();
		if(node.getClassName()!=null && node.getClassName().equals("android.widget.RelativeLayout")){
			int tempChild=node.getChildCount();
			if(tempChild==4){
                //check its Contact type
                AccessibilityNodeInfo firstChild=node.getChild(0);
                AccessibilityNodeInfo secondChild=node.getChild(1);
                AccessibilityNodeInfo thirdChild=node.getChild(2);
                AccessibilityNodeInfo fourthChild=node.getChild(3);
                if(firstChild!=null && firstChild.getClassName()!=null && firstChild.getClassName().equals("android.widget.ImageView")
                        && secondChild!=null && secondChild.getClassName()!=null && secondChild.getClassName().equals("android.widget.TextView")
                        && thirdChild!=null && thirdChild.getClassName()!=null && thirdChild.getClassName().equals("android.widget.TextView")
                        && fourthChild!=null &&  fourthChild.getClassName()!=null && fourthChild.getClassName().equals("android.widget.TextView")
                        ){
                    if(thirdChild.getText()==null && secondChild.getText()!=null){
//                        groupContactSet.add(secondChild.getText().toString());
                    }
                }
			}
		}
        //checkFullScreen(node);
        //long daysBetween= TimeUnit.MILLISECONDS.toDays(new Date().getTime() - DefaultPrefSettings.getInstance().getVisibleDate());
        //if(daysBetween>=1){
            //checkShareScreen(node);
        //}
		for (int i = 0; i < child; i++) {
			getAllChildNode(node.getChild(i));
		}
	}

}
