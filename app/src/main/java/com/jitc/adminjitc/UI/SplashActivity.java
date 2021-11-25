package com.jitc.adminjitc.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.jitc.adminjitc.login.LoginActivity;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.login.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Handler handler = new Handler();

        final SharedPrefManager sharedPrefManager = new SharedPrefManager(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefManager.getIsLogin()){
                    Intent i = new Intent(SplashActivity.this, MenuActivity.class);
                    finishAffinity();
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    finishAffinity();
                    startActivity(i);
                }
            }
        }, 3000);

    }
}