package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import android.os.Handler;
public class MainActivity extends AppCompatActivity {


    //database reference
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    StorageReference reference = FirebaseStorage.getInstance().getReference("Image");
    //
    private ArrayList<userData> list;
    private UserAdapter padapter;
    FirebaseAuth firebaseAuth;
    Button btn_signout;
    private Uri imageUri;
    CardView btnhome, btnMarket, btnTips,btnFQA;
    private DrawerLayout drawer;
    ImageView humbergerMenu;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    private Handler handler;
    private Runnable scrollRunnable;
    private int scrollPosition = 0;
    private int delayMillis = 10; // Adjust the delay for smoother scrolling

    private ArrayList<Orders> orderList;
    private recentbooking_adapter orderAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hiding app bar
        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerViewhome);
        recyclerView.setHasFixedSize(false);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        orderList = new ArrayList<>();
        orderAdapter = new recentbooking_adapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

        // Initialize Firebase components
        databaseRef = FirebaseDatabase.getInstance().getReference("Order");
        firebaseAuth = FirebaseAuth.getInstance();

        // Retrieve data from Firebase
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Orders order = dataSnapshot.getValue(Orders.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                orderAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        handler = new Handler();
        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (layoutManager != null && recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    scrollPosition = (scrollPosition + 1) % itemCount;
                    recyclerView.smoothScrollToPosition(scrollPosition);
                    handler.postDelayed(this, 3000); // 3 seconds delay
                }
            }
        };
        // Start the scrolling
        handler.postDelayed(scrollRunnable, 3000); // Initial delay

        //drawer side navigation buttons

        drawerLayout = findViewById(R.id.draweLayout);
        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
//        drawer = findViewById(R.id.draweLayout);
//       Toolbar toolbar = findViewById(R.id.toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        //btn to new activity
        btnhome = findViewById(R.id.btn1);
        btnhome.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Home.class)));
        btnMarket = findViewById(R.id.btn2);
        btnMarket.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Market.class)));
        btnTips = findViewById(R.id.btn3);
        btnTips.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Tips.class)));
        btnFQA = findViewById(R.id.btn4);
        btnFQA.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FQA.class)));
        ImageView  humbager=findViewById(R.id.humbMenu);

        //clicking hamburger button to open menu bar
        humbager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
                //Toast.makeText(MainActivity.this, "humbler clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_signout=  findViewById(R.id.Sign_out_btn);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                }
            }
        });

//        list = new ArrayList<>();
//        padapter = new UserAdapter(this , list);
//       // drawerLayout.setAdapter(padapter);
//        root.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    userData model = dataSnapshot.getValue(userData.class);
//                    list.add(model);
//                }
//                padapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
    }



    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Handle navigation view item clicks here.
                        int id = menuItem.getItemId();

                        if (id == R.id.signoutnav) {
                            // Handle the first item action
                            //  onItemSelected(id);

                        } else if (id == R.id.humbMenu) {
                            // Handle the second item action
                        }

                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }



//    @SuppressLint("NonConstantResourceId")
//    private void onItemSelected(int id) {
//        // Perform action when the selected item is clicked
//        switch (id) {
//            case R.id.humbMenu:
//                // Your code here
//                if (firebaseAuth.getCurrentUser() != null) {
//                    firebaseAuth.signOut();
//                    startActivity(new Intent(MainActivity.this, Login.class));
//                    finish();
//                }
//
//                break;
//            default:
//                break;
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(scrollRunnable);
        }
    }
}