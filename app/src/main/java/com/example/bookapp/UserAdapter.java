package com.example.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private final ArrayList<userData> mList;
    private final Context context;
    public UserAdapter(MainActivity context , ArrayList<userData> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.drawer_header , parent ,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getUserName()).into(holder.pimage);
        holder.Pname.setText(mList.get(position).getPhone());
        //holder.textView1.setText(mList.get(position).getDescription());
        //holder.textView3.setText(mList.get(position).getMtitle());
//
//        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(mList.get(position).getUserName());
//
//        Glide.with(context)
//                .load(storageReference)
//                .into(holder.pimage);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView1,Pname,textView3;
        ImageView pimage;
        CardView bt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pimage = itemView.findViewById(R.id.ProfileImageView);
            Pname = itemView.findViewById(R.id.ProfileName);
            //textView2 = itemView.findViewById(R.id.Locationtxt);
            //textView3 = itemView.findViewById(R.id.titlename);

        }
    }

}
