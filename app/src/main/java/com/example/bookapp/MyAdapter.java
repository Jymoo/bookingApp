package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    private final ArrayList<Model> mList;
    private final Context context;

    public MyAdapter(Market context, ArrayList<Model> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getmImageUrl()).into(holder.imageView);
        holder.textView1.setText(mList.get(position).getMdesc());
        holder.textView2.setText(mList.get(position).getMlocatn());
        holder.textView3.setText(mList.get(position).getMtitle());

//test image model
        ImageModel imageModel = new ImageModel(mList.get(position).getmImageUrl());
        holder.orderbtn.setTag(imageModel);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2, textView3;
        ImageView imageView;
        private Context Intentnext;
        MaterialButton orderbtn, Enquirebtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.postimage);
            textView1 = itemView.findViewById(R.id.decribetxt1);
            textView2 = itemView.findViewById(R.id.Locationtxt);
            textView3 = itemView.findViewById(R.id.titlename);
            Enquirebtn = itemView.findViewById(R.id.Inquire_btn);
            orderbtn = itemView.findViewById(R.id.order_btn);

            // call item btn
            orderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(v.getContext(), OrderPage.class);
                    ImageModel imageModel = (ImageModel) v.getTag();
                    i.putExtra("imageModel", imageModel);
                    v.getContext().startActivity(i);
                   // v.getContext().startActivity(new Intent(v.getContext(), OrderPage.class));
                    String title = textView3.getText().toString();
                    String description = textView1.getText().toString();
                  // sendDataToFirebase(title, description, data);
                    sendDataToFirebase(title, description);

                }
            });

            Enquirebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(), Enquiry.class));
                }


            });

        }

        private void sendDataToFirebase(String title, String description) {
            FirebaseDatabase root = FirebaseDatabase.getInstance();
            DatabaseReference myRef = root.getReference("item_DTO");
            item_DTO card = new item_DTO(title, description);
            myRef.setValue(card);

    }}
}


