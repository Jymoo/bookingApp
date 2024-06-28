package com.example.bookapp;

import static java.util.Locale.filter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tips extends AppCompatActivity {

    private ArrayList<TipsModel> list;
    private TipsAdapter adapter1;
    SwipeRefreshLayout SwipeRefreshL;
    RelativeLayout itemlayouttip;
    LinearLayout btnhometip, Tipbtn, btnMkt, orderhist;
    RecyclerView recyclerView1;
    RelativeLayout rsearchbar;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Tips");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
//
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

        //Making searchbar overlay other objects
        itemlayouttip=findViewById(R.id.item_search_holder);
        itemlayouttip.bringToFront();

        btnMkt=findViewById(R.id.Market_tips);
        btnMkt.setOnClickListener(v -> startActivity(new Intent(Tips.this,Market.class )));

        //
        btnhometip=findViewById(R.id.homebtn_tips);
        btnhometip.setOnClickListener(v -> startActivity(new Intent(Tips.this,MainActivity.class )));
        //

//        //btn to Tip
        Tipbtn=findViewById(R.id.Tips_tips);
        Tipbtn.setOnClickListener(v -> startActivity(new Intent(Tips.this, Tips.class )));


        orderhist=findViewById(R.id.tip_orderhistory);
        orderhist.setOnClickListener(v -> startActivity(new Intent(Tips.this, Order_history.class )));

//        //fab button previous
        FloatingActionButton fab = findViewById(R.id.add_fab_tips);
        fab.setOnClickListener(view -> startActivity(new Intent(Tips.this, AddTips.class)));

        recyclerView1 = findViewById(R.id.recyclerViewTip);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));

        recyclerView1= findViewById(R.id.recyclerViewTip);
        list = new ArrayList<>();
        adapter1 = new TipsAdapter(this , list);
        recyclerView1.setAdapter(adapter1);
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TipsModel model = dataSnapshot.getValue(TipsModel.class);
                    list.add(model);

                }

                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }



//    private void dialog(){
//        AlertDialog alertdialog = new AlertDialog.Builder(Tips.this).create();
//        alertdialog.setTitle("Order status");
//        alertdialog.setMessage("Your order is currently on the way and we will notify you when it has arrived");
//        alertdialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        alertdialog.show();
//    }

}
