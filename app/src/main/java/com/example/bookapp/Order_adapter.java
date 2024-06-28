package com.example.bookapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Order_adapter extends RecyclerView.Adapter<Order_adapter.MyViewHolder> {
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE =1;
    private final ArrayList<Orders> mList;
    private final Context context;
    public Order_adapter(Order_history context , ArrayList<Orders> mList){

        this.context = context;
        this.mList = mList;

    }


    @NonNull
    @Override
    public Order_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_item , parent ,false);
        return new Order_adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImageUri()).into(holder.imageView);
        holder.textView1.setText(mList.get(position).getAmount());
        holder.textView2.setText(mList.get(position).getLocation());
        holder.textView3.setText(mList.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private Button statusBtn;
        private CountDownTimer countDownTimer;
        TextView textView1,textView2,textView3;
        ImageView imageView;
        private Context Intentnext;
        MaterialButton btncancel, Enquirebtn,btnprogress;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.order_image);
            textView1 = itemView.findViewById(R.id.order_descriptions);
            textView2 = itemView.findViewById(R.id.pic_location);
            textView3 = itemView.findViewById(R.id.order_name);
            Enquirebtn = itemView.findViewById(R.id.Inquire_btn);
            btncancel = itemView.findViewById(R.id.order_cancel_btn);
            btnprogress=itemView.findViewById(R.id.status_btn);

            //cancelButton
            btncancel.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    AlertDialog alertdialog2 = new AlertDialog.Builder(v.getContext()).create();
                    alertdialog2.setTitle("Cancel Booking Alert");
                    alertdialog2.setMessage("Dear Customer, You cannot cancel booking which is in progress, doing so will attract a penalty on your next booking. To accept the terms of cancellation press ok to continue");
                    alertdialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertdialog2.show();

                    int position = getAdapterPosition();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mList.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                        }
                    }, 3000); // Delay of 1 second (1000 milliseconds)

                }
            });


            // call item btn
            btnprogress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertdialog1 = new AlertDialog.Builder(v.getContext()).create();
                    alertdialog1.setTitle("Booking status");
                    alertdialog1.setMessage("Dear Valued customer, Your booking is succesully reserved");
                    alertdialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertdialog1.show();
                    countDownTimer = new CountDownTimer(20 * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            btnprogress.setBackgroundColor(v.getContext().getResources().getColor(R.color.DimGray));
                            btnprogress.setText("Processing...");
                        }
                        public void onFinish() {
                            btnprogress.setBackgroundColor(v.getContext().getResources().getColor(R.color.colorSecondaryText));
                            btnprogress.setText("Success");
                        }

                    }.start();
                }
            });


            Enquirebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(), Enquiry.class));
                }
            });

        }

    }

}



