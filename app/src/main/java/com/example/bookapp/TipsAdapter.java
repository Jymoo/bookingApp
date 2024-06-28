package com.example.bookapp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {

    private final ArrayList<TipsModel> mList;
    private final Context context;
    public TipsAdapter(Tips context , ArrayList<TipsModel> mList){

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
        Glide.with(context).load(mList.get(position).getmImageUrl()).into(holder.imageView);
        holder.textView1.setText(mList.get(position).getMdesc());
        holder.textView2.setText(mList.get(position).getMlocatn());
        holder.textView3.setText(mList.get(position).getMtitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView1,textView2,textView3;
        ImageView imageView;
        CardView bt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.postimage);
            textView1 = itemView.findViewById(R.id.decribetxt1);
            textView2 = itemView.findViewById(R.id.Locationtxt);
            textView3 = itemView.findViewById(R.id.titlename);
            bt = itemView.findViewById(R.id.btnlike);
            bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        AlertDialog alertdialog = new AlertDialog.Builder(v.getContext()).create();
                        alertdialog.setTitle("Tips Comments");
                        alertdialog.setMessage("Dear member,  This feature is currently under maintenance");
                        alertdialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertdialog.show();
                }
            });

        }

    }

}