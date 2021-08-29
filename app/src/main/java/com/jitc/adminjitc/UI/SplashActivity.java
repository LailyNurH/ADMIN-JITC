package com.jitc.adminjitc.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.jitc.adminjitc.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, MenuActivity.class);
            finishAffinity();
            startActivity(i);
        }, 2500);
    }
}