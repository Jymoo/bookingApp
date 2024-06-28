package com.example.bookapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class recentbooking extends AppCompatActivity {


    private ArrayList<Orders> list;
    private recentbooking_adapter order_AdapterRecent;
    RecyclerView recyclerViewRecent;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Order");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Hide the app bar
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }

        recyclerViewRecent = findViewById(R.id.recyclerViewhome);
        recyclerViewRecent.setHasFixedSize(true);
        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        list = new ArrayList<>();
        order_AdapterRecent = new recentbooking_adapter(this, list);
        recyclerViewRecent.setAdapter(order_AdapterRecent);

        // Retrieve data from Firebase
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Orders order = dataSnapshot.getValue(Orders.class);
                    if (order != null) {
                        list.add(order);
                    }
                }
                order_AdapterRecent.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
