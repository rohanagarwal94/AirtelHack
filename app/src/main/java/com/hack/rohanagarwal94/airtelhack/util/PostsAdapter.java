package com.hack.rohanagarwal94.airtelhack.util;

/**
 * Created by rohanagarwal94 on 26/7/16.
 */

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.hack.rohanagarwal94.airtelhack.model.Loan;
import com.rnrapps.user.dtuguide.AppController;
import com.rnrapps.user.dtuguide.R;
import com.rnrapps.user.dtuguide.Utils;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private Activity activity;
    private List<Loan> feedItems;
    private AdapterCallback mAdapterCallback;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, title;
        public ImageView imageView;
        public int amount;

        public MyViewHolder(View view) {
            super(view);
            view.bringToFront();
            name = (TextView) view
                    .findViewById(R.id.);
            amount = (TextView) view
                    .findViewById(R.id.timestamp);
            title = (TextView) view
                    .findViewById(R.id.txtStatusMsg);
            imageView = (ImageView) view.findViewById(R.id.txtUrl);

        }
    }
    
    public PostsAdapter(Activity activity,List<FeedItem> feedItems) {
        this.feedItems=feedItems;
        this.activity=activity;
        try {
            this.mAdapterCallback = ((AdapterCallback) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Loan item=feedItems.get(position);

        String a=item.getTimeStamp();
        holder.timestamp.setText(Utils.getTimeFromTimestamp(a));

        if (!TextUtils.isEmpty(item.getStatus())) {
            holder. statusMsg.setText(item.getStatus());
            holder.statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            holder.statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            holder.url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            holder.url.setMovementMethod(LinkMovementMethod.getInstance());
            holder.url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            holder.url.setVisibility(View.GONE);
        }

        if (item.getImge() != null) {
            holder.feedImageView.setImageUrl(item.getImge(), imageLoader);
            holder.feedImageView.setVisibility(View.VISIBLE);
            holder.feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            holder.feedImageView.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return feedItems.size();
    }


}