package com.example.bookapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import androidx.annotation.NonNull;
import android.provider.ContactsContract;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;


public class Uploading extends AppCompatActivity {
    //widgets
    TextView upltext,lction,Ttletxt;
    private ImageView imageView;
    private ProgressBar progressBar;
    // FirebaseDatabase name1;

    //db reference
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    StorageReference reference = FirebaseStorage.getInstance().getReference("Image");
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        Button uploadBtn = findViewById(R.id.uploadbtn);
        Button showAllBtn = findViewById(R.id.btnshow);
        upltext = findViewById(R.id.discriptiontext);
        lction=findViewById(R.id.locatUpld);
        Ttletxt=findViewById(R.id.Titletxt);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.m_image);

        progressBar.setVisibility(View.INVISIBLE);

        showAllBtn.setOnClickListener(v -> {
            startActivity( new Intent(Uploading.this , Market.class));
            finish();
        });
        //intent to choose image from phone files
        imageView.setOnClickListener(v -> {

            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,2);
        });

        uploadBtn.setOnClickListener(v -> {

            if (imageUri != null){
                uploadToFirebase(imageUri);

            }else{
                Toast.makeText(Uploading.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getData() != null) {

                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }

    public void uploadToFirebase(Uri uri){
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().
                addOnSuccessListener(uri1 -> {

                    Model model = new Model(uri1.toString(), upltext.getText().toString(),lction.getText().toString(),Ttletxt.getText().toString());
                    String modelId = root.push().getKey();
                    assert modelId != null;
                    root.child(modelId).setValue(model);

                    // DescriptionClass();

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Uploading.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    upltext.setText("Description...");
                    lction.setText("Location...");
                    Ttletxt.setText("Title here...");


                })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Uploading.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}









