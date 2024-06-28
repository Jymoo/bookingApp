package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profile extends AppCompatActivity {
    EditText etFirstName, etjob, etEmail, etContactNo, etDec;
    final int MIN_PASSWORD_LENGTH = 6;
    private Uri imageUri;
    Button btreg;
    ImageView UserImage;

    //db reference
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Profile");
    StorageReference reference = FirebaseStorage.getInstance().getReference("Profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        viewInitializations();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getData() != null) {

                imageUri = data.getData();
                UserImage.setImageURI(imageUri);

            }
        }
    }


    void viewInitializations() {
        UserImage=findViewById(R.id.userImage);
        btreg=findViewById(R.id.bt_register);
        etFirstName = findViewById(R.id.et_first_name);
        etjob = findViewById(R.id.et_job);
        etEmail = findViewById(R.id.et_email);
        etContactNo = findViewById(R.id.et_contact_no);
        etDec = findViewById(R.id.et_des);

        UserImage.setOnClickListener(v -> {

            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,2);


        });

        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);}
    // Checking if the input in form is valid
    boolean validateInput() {
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etjob.getText().toString().equals("")) {
            etjob.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etContactNo.getText().toString().equals("")) {
            etContactNo.setError("Please Enter Contact No");
            return false;
        }
        if (etDec.getText().toString().equals("")) {
            etDec.setError("Please Enter Designation ");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }

        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performEditProfile (View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String firstName = etFirstName.getText().toString();
            String lastName = etjob.getText().toString();
            String email = etEmail.getText().toString();
            String contactNo = etContactNo.getText().toString();
            String Designation = etDec.getText().toString();

            // Here you can call you API

            if (imageUri != null){
                    uploadToFirebase(imageUri);

                }else{
                    Toast.makeText(Profile.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }

        }
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().
                addOnSuccessListener(uri1 -> {

                    userData Data_User = new userData(uri1.toString(), etFirstName.getText().toString(),etjob.getText().toString(),etEmail.getText().toString(),etContactNo.toString(),etDec.toString());
                    String userDataID = root.push().getKey();
                    assert userDataID != null;
                    root.child(userDataID).setValue(Data_User);

                    // DescriptionClass();
                    Toast.makeText(Profile.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                })).addOnFailureListener(e ->{
            Toast.makeText(Profile.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}