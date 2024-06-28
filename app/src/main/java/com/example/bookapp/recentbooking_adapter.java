package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class recentbooking_adapter extends RecyclerView.Adapter<recentbooking_adapter.SimpleViewHolder> {

    private final ArrayList<Orders> mList;
    private final Context context;

    public recentbooking_adapter(Context context, ArrayList<Orders> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public recentbooking_adapter.SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recentbook_item, parent, false);
        return new recentbooking_adapter.SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull recentbooking_adapter.SimpleViewHolder holder, int position) {
        Orders order = mList.get(position);
        holder.titleTextView.setText(order.getProductName());
        holder.locationTextView.setText(order.getLocation());
        holder.descriptionTextView.setText(order.getAmount());
        Glide.with(context).load(mList.get(position).getImageUri()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView titleTextView, locationTextView, descriptionTextView;

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.order_title);
            locationTextView = itemView.findViewById(R.id.order_location);
            descriptionTextView = itemView.findViewById(R.id.order_description);
            imageView = itemView.findViewById(R.id.recent_booking_image);
        }
    }
}
