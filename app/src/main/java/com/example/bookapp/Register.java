package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    Button btn2_signup;
    EditText user_name, pass_word,name1, date1, tvSelectedDate;
    FirebaseAuth mAuth;
    private int mYear, mMonth, mDay;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }
//calendar view
//        tvSelectedDate=findViewById(R.id.tvSelectedDate);
//
//        findViewById(R.id.datePicker).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c =Calendar.getInstance();
//                mYear=c.get(Calendar.YEAR);
//                mMonth=c.get(Calendar.MONTH);
//                mDay=c.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog= new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        tvSelectedDate.setText(dayOfMonth +"-" +(monthOfYear + 1) +"-"+year);
//                    }
//                },mYear ,mMonth ,mDay);
//                datePickerDialog.show();
//            }
//        });

        //name1=findViewById(R.id.name);
//        date1=findViewById(R.id.date);
        user_name=findViewById(R.id.username);
        pass_word=findViewById(R.id.password1);
        btn2_signup=findViewById(R.id.sign);
        mAuth=FirebaseAuth.getInstance();
        btn2_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_name.getText().toString().trim();
                String password= pass_word.getText().toString().trim();

                //additional signup
                //String name = name1.getText().toString().trim();
               // String date= date1.getText().toString().trim();
                // Write a message to the database
                //still in progress


                if(email.isEmpty())
                {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_name.setError("Enter the valid email address");
                    user_name.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass_word.setError("Enter the password");
                    pass_word.requestFocus();
                    return;
                }
                if(password.length()<6)
                {
                    pass_word.setError("Length of the password should be more than 6");
                    pass_word.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this,"You are successfully Registered", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Register.this, Login.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this,"You are not Registered! Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}
