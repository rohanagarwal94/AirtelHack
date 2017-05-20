package com.hack.rohanagarwal94.airtelhack.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.api.ClientBuilder;
import com.hack.rohanagarwal94.airtelhack.config.Constants;
import com.hack.rohanagarwal94.airtelhack.config.DefaultPrefSettings;
import com.hack.rohanagarwal94.airtelhack.model.FundTransferResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rohanagarwal94 on 29/6/16.
 */

public class DialogView extends LinearLayout {
    private Context context;
    private String amount;
    private String destAccount;
    private View loadingOverlay;
    private View contentOverlay;
    private TextView okay;
    private TextView cancel;


	public DialogView(Context context) {
		super(context);
        this.context = context;
        init(context);
	}

	private void init(final Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.tags_suggestion_view, this);
        loadingOverlay=findViewById(R.id.loading_overlay);
        contentOverlay=findViewById(R.id.content_overlay);
        okay = (TextView) findViewById(R.id.ok);
        okay.setOnClickListener(okClickListener);
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(cancelClickListener);
        ((ImageView)findViewById(R.id.close_view)).setOnClickListener(closeViewListener);
    }

    public void setAccountAndAmount(String destAccount, String amount)
    {
        this.destAccount = destAccount;
        this.amount = amount;
    }

    private View.OnClickListener okClickListener=new View.OnClickListener(){
      public void onClick(View v){

          new ClientBuilder().getIciciApi().fundTransfer(Constants.CLIENT_ID, Constants.ACCESS_TOKEN, Constants.SOURCE_ACC, destAccount, amount, "NA", 0, "PMR").enqueue(new Callback<List<FundTransferResponse>>() {
              @Override
              public void onResponse(Call<List<FundTransferResponse>> call, Response<List<FundTransferResponse>> response) {
                  Toast.makeText(context, "Transaction successful", Toast.LENGTH_LONG).show();
                  double balance = Double.parseDouble(DefaultPrefSettings.getInstance().getBalance());
                  balance = balance - Double.parseDouble(amount);
                  DefaultPrefSettings.getInstance().setBalance(Double.toString(balance));
                  setVisibility(View.GONE);
              }

              @Override
              public void onFailure(Call<List<FundTransferResponse>> call, Throwable t) {
                  t.printStackTrace();
                  Log.d("url",call.request().url().toString());
                  Toast.makeText(context, "Transaction failed", Toast.LENGTH_LONG).show();
                  setVisibility(View.GONE);
              }
          });
          setOverlayVisibility(false);
      }
    };

    private View.OnClickListener cancelClickListener=new View.OnClickListener(){
        public void onClick(View v){
//          Toast.makeText(context,"cancel clicked", Toast.LENGTH_SHORT).show();
            setVisibility(View.GONE);
        }
    };

    private View.OnClickListener closeViewListener=new View.OnClickListener(){
        public void onClick(View v){
            setVisibility(View.GONE);
        }
    };

//	public void bind(List<String> tagsFromImage, List<String> usualTags, List<String> popularTags) {
//        setOverlayVisibility(false);
//	}

    public void setOverlayVisibility(boolean visible){
        if(visible){
            loadingOverlay.setVisibility(INVISIBLE);
            contentOverlay.setVisibility(VISIBLE);
        }else{
            loadingOverlay.setVisibility(VISIBLE);
            contentOverlay.setVisibility(INVISIBLE);
        }

    }
}
