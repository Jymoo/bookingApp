package com.example.bookapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Handler handler, handler1;
    Animation animation_moveUp, animation_zoom, slide_anime,move_Up_anime;
    ImageView imageLogo;
    TextView textViewTitle, yeartxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //add animations to objects
        imageLogo=findViewById(R.id.logoImage);
        textViewTitle=findViewById(R.id.txtTitle);
        yeartxt=findViewById(R.id.yearhome);

        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //LOGO animation
                animation_moveUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);
                imageLogo.setVisibility(View.VISIBLE);
                imageLogo.startAnimation(animation_moveUp);
            }
        },5);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                            animation_zoom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_anime);
                            slide_anime = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_anime);
                            textViewTitle.startAnimation(animation_zoom);
                            textViewTitle.startAnimation(slide_anime);

                Intent intent=new Intent(SplashScreen.this,Login.class);
                startActivity(intent);
                finish();
            }
        },6000);

    }
}