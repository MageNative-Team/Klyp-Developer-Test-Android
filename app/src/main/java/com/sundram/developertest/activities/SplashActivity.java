package com.sundram.developertest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sundram.developertest.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            },1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}