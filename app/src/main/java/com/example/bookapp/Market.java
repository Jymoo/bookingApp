package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Market extends AppCompatActivity {
    private ArrayList<Model> list;
    private MyAdapter adapter;
    SwipeRefreshLayout SwipeRefresh;
    EditText upltext;
    public ImageView imageView;
    RelativeLayout itemlayout;
    LinearLayout btnhome,Tipbtn, btnmkt, myOrdermkt;
    private ProgressBar progressBar;
    int SELECT_PICTURE = 200;

    //db reference
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    StorageReference reference = FirebaseStorage.getInstance().getReference("Image");
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);


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
        itemlayout=findViewById(R.id.item_search_holder);
        itemlayout.bringToFront();


//        SwipeRefresh=findViewById(R.id.refreshTab);
//        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                SwipeRefresh.setRefreshing(false);
//            }
//        });

        //back to homepage
        btnhome=findViewById(R.id.homebtn_market);
        btnhome.setOnClickListener(v -> startActivity(new Intent(Market.this,MainActivity.class )));
        //
        btnmkt=findViewById(R.id.Market_market);
        btnmkt.setOnClickListener(v -> startActivity(new Intent(Market.this, Market.class )));
//        //btn to Tip
        Tipbtn=findViewById(R.id.Tips_Market);
        Tipbtn.setOnClickListener(v -> startActivity(new Intent(Market.this, Tips.class )));

        myOrdermkt=findViewById(R.id.Myordermkt);
        myOrdermkt.setOnClickListener(v -> startActivity(new Intent(Market.this, Order_history.class )));

//        //
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        list = new ArrayList<>();
        adapter = new MyAdapter(this , list);
        recyclerView.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//fab button previous
        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(view -> startActivity(new Intent(Market.this, Uploading.class)));

//        FloatingActionButton fab = findViewById(R.id.add_fab);
//        fab.setOnClickListener(v -> showDialog());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                imageUri = data.getData();
                if (null != imageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(imageUri);


                }
            }
        }
    }

    //uploading image  to be implemented
    public void uploadToFirebase(Uri uri){
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().
                addOnSuccessListener(uri1 -> {

                    Model model = new Model(uri1.toString(), upltext.getText().toString());
                    String modelId = root.push().getKey();
                    assert modelId != null;
                    root.child(modelId).setValue(model);

                //    progressBar = findViewById(R.id.progressBar);

                    Toast.makeText(Market.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    // DescriptionClass();

                    progressBar.setVisibility(View.INVISIBLE);

                    imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    upltext.setText("Description...");
                    //lction.setText("Location...");
                    //Ttletxt.setText("Title here...");


                })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Market.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}
