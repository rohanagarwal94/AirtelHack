package com.hack.rohanagarwal94.airtelhack.util;

/**
 * Created by rohanagarwal94 on 26/7/16.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.config.Constants;
import com.hack.rohanagarwal94.airtelhack.model.Loan;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder>{

    private Activity activity;
    private List<Loan> feedItems;

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, title, amount;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            view.bringToFront();
            name = (TextView) view
                    .findViewById(R.id.track_description);
            amount = (TextView) view
                    .findViewById(R.id.amt_left);
            title = (TextView) view
                    .findViewById(R.id.track_title);
            imageView = (ImageView) view.findViewById(R.id.imageView3);

        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getPosition()); //OnItemClickListener mItemClickListener;
        }
    }
    
    public PostsAdapter(Activity activity,List<Loan> feedItems) {
        this.feedItems=feedItems;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loan, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Loan item=feedItems.get(position);

        Log.i("PostsAdapter",item.getName());
        String name = item.getName();
        String title = item.getTitle();
        float amount = item.getAmountLeft();
        holder.title.setText(title);
        holder.name.setText(name);
        holder.amount.setText(Float.toString(amount));
        int type = item.getType();
        holder.imageView.setImageDrawable(activity.getResources().getDrawable(Constants.imageDrawable.get(Integer.valueOf(type))));
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

}