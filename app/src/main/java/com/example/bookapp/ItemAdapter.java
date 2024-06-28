package com.example.bookapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private final ArrayList<item_DTO> mList;
    private final Context context;
    public ItemAdapter(OrderPage context , ArrayList<item_DTO> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tips_item , parent ,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Glide.with(context).load(mList.get(position).getmImageUrl()).into(holder.imageView);
        holder.textView3.setText(mList.get(position).getTitle());
        holder.textView1.setText(mList.get(position).getDescription());
        //holder.textView3.setText(mList.get(position).getMtitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView1,textView2,textView3;
        ImageView imageView;
        CardView bt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //imageView = itemView.findViewById(R.id.postimage);
            textView1 = itemView.findViewById(R.id.decribetxt1);
            //textView2 = itemView.findViewById(R.id.Locationtxt);
            textView3 = itemView.findViewById(R.id.titlename);

        }
    }

}
