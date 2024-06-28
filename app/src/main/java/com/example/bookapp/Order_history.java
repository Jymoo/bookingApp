package com.example.bookapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Order_history extends AppCompatActivity {
    private ArrayList<Orders> list;
    private Order_adapter order_Adpter;
    //private TipsAdapter adapter1;
    RecyclerView recyclerView3;
    public ImageView imageView;
    RelativeLayout itemlayout;
    LinearLayout histbtnhome,histTipbtn, hist_btnMkt,histAccbtn,hist_order_btn;
    private ProgressBar progressBar;
    int SELECT_PICTURE = 200;

    //db reference
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Order");
    StorageReference reference = FirebaseStorage.getInstance().getReference("Order");
    private Uri imageUri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //hiding app bar
        try {
            this.getSupportActionBar().hide();}
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        hist_btnMkt=findViewById(R.id.hist_market);
        hist_btnMkt.setOnClickListener(v -> startActivity(new Intent(Order_history.this,Market.class )));

        //
        histbtnhome=findViewById(R.id.hist_homebtn);
        histbtnhome.setOnClickListener(v -> startActivity(new Intent(Order_history.this,MainActivity.class )));
        //

//        //btn to Tip
        histTipbtn=findViewById(R.id.histbtntips);
        histTipbtn.setOnClickListener(v -> startActivity(new Intent(Order_history.this, Tips.class )));


        hist_order_btn=findViewById(R.id.hist_order_btn);
        hist_order_btn.setOnClickListener(v -> startActivity(new Intent(Order_history.this, Order_history.class )));


        histAccbtn=findViewById(R.id.hist_myaccount);
        histAccbtn.setOnClickListener(v -> startActivity(new Intent(Order_history.this, Profile.class )));


        recyclerView3 = findViewById(R.id.recyclerView_order);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        list = new ArrayList<>();
        order_Adpter  = new Order_adapter(this, list);
        recyclerView3.setAdapter(order_Adpter);
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Orders order = dataSnapshot.getValue(Orders.class);
                    list.add(order);
                }
                order_Adpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }



}
