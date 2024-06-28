package com.example.bookapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class OrderPage extends AppCompatActivity {
    EditText product_name,Amnt;
    TextView PicLoction;
    Button btn_signout;
    private Uri imageUri,fileUri;
    String imageUrl;
    private ProgressBar probar;
    private ImageView imageView;
    RecyclerView recyclerView2;
    private ArrayList<item_DTO> list;
    private ItemAdapter adapter2;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Order");
    StorageReference reference = FirebaseStorage.getInstance().getReference("Order");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //hiding app bar
        try {this.getSupportActionBar().hide();}
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        Button SubmitOrder =findViewById(R.id.Confirmbtn);
        Button backbtn =findViewById(R.id.backbtn);
        Button btnhome2 =findViewById(R.id.homebtn2);
        Button btnmyOrder =findViewById(R.id.Myoderbtn);
        imageView=findViewById(R.id.imageView5);
        probar= findViewById(R.id.progressBar1);

        probar.setVisibility(View.INVISIBLE);

        // Reference to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataRef = databaseReference.child("item_DTO");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                        String value = entry.getValue();
                        Log.d("key", entry.getKey());
                        Log.d("value", entry.getValue());
                        product_name.setText(value);
                        //Amnt.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
        //dropdown list of products
        Spinner locationSpinner = findViewById(R.id.location_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = parent.getItemAtPosition(position).toString();
                PicLoction.setText("My location: " + selectedLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> view) {
            }
        });


        //initialize objects
        product_name=findViewById(R.id.PrdNametxt);
        Amnt=findViewById(R.id.ProdPrize);
        PicLoction = findViewById(R.id.locatUpld);
        btnmyOrder.setOnClickListener(v -> {startActivity( new Intent(OrderPage.this , Order_history.class));
            finish();
        });
        btnhome2.setOnClickListener(v -> {startActivity( new Intent(OrderPage.this , MainActivity.class));
            finish();
        });
        backbtn.setOnClickListener(v -> {startActivity( new Intent(OrderPage.this , Market.class));
            finish();
        });

        SubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertdialog1 = new AlertDialog.Builder(OrderPage.this).create();
                alertdialog1.setTitle("Reservation Successfully created");
                alertdialog1.setMessage("Dear Customer, Your booking has been received, please check on My booking page to manage your booking.");
                alertdialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                if (!validateInput()) {
                    performEditOrder();
                }


                else if (fileUri != null){
                    //upload to firebase API CALL
                        uploadToFirebase1(fileUri);
                        alertdialog1.show();

                }
            }
        });

//calling perceble image and download the url in app
        ImageModel imageModel = getIntent().getParcelableExtra("imageModel");
        String imageUrl = imageModel.getImageUrl();
        Log.d("imageUrl", imageUrl);
        Glide.with(this).load(imageUrl).into(imageView);
        imageView.setTag(imageUrl);
        //Log.d("imageUrl", imageUrl);
        imageUri = Uri.parse(imageUrl);
        imageView.setImageURI(imageUri);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        // Save the bitmap to a local file
                        File file = new File(getCacheDir(), "image.jpg");
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Get the Uri of the file
                        fileUri = Uri.fromFile(file);
                        // Use fileUri with fileRef.putFile to upload the file
                        // ...
                    }
                });
        Uri uri= Uri.parse(imageUrl);
        imageView.setImageURI(uri);


        //  sign_out
        btn_signout=  findViewById(R.id.signoutbtn);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(OrderPage.this, Login.class));
                    finish();
                }
            }
        });

    }


    @SuppressLint("LongLogTag")
    public void performEditOrder() {
        if (validateInput()) {

            // Input is valid, here send data to your server
            String firstName = product_name.getText().toString();
            String lastName = Amnt.getText().toString();
            String location = PicLoction.getText().toString();
            // Here you can call you API
            if (imageUri != null){
                uploadToFirebase1(imageUri);
            }

            }else{
                Toast.makeText(OrderPage.this, "please fill all fields", Toast.LENGTH_SHORT).show();
            }

        }



//original code
    private void uploadToFirebase1(Uri fileUri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(fileUri));
        fileRef.putFile(fileUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().
                addOnSuccessListener(uri1 -> {

                    Orders user_order = new Orders(uri1.toString(), product_name.getText().toString(),Amnt.getText().toString(),PicLoction.getText().toString());
                    String OrderID = root.push().getKey();
                    assert OrderID != null;
                    root.child(OrderID).setValue(user_order);

                    probar.setVisibility(View.INVISIBLE);

                    // DescriptionClass();
                    Toast.makeText(OrderPage.this, "Booking Submitted Successfully", Toast.LENGTH_SHORT).show();

                })).addOnProgressListener(snapshot -> probar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
            probar.setVisibility(View.INVISIBLE);
            Toast.makeText(OrderPage.this, "Booking Failed !!", Toast.LENGTH_SHORT).show();
        });
    }
//
    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    //Validate input fields
    private boolean validateInput() {
        if (product_name.getText().toString().equals("")) {
            product_name.setError("Please enter Reservation name");
            return false;
        }
        if (Amnt.getText().toString().equals("")) {
            Amnt.setError("Please enter amount");
            return false;
        }
        if (PicLoction.getText().toString().equals("")) {
            PicLoction.setError("Please Enter your booking location");
            return false;
        }

        if (imageUri == null){

            Toast.makeText(OrderPage.this, "No image found", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}

