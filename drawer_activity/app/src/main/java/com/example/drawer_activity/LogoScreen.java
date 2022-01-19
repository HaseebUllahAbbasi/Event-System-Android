package com.example.drawer_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LogoScreen extends AppCompatActivity  implements Animation.AnimationListener{

    ImageView imageView;
    View view;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_screen);

         animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myanimation);
        imageView = findViewById(R.id.imageView3);

        animation.setAnimationListener(this);

        imageView.setVisibility(View.VISIBLE);

        imageView.startAnimation(animation);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);

    }

    public void HomeScreen(View view)
    {
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}