package com.jitc.adminjitc.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.jitc.adminjitc.login.LoginActivity;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.login.SharedPrefManager;
import com.jitc.adminjitc.pendaftaran.ListPendaftarActivity;
import com.jitc.adminjitc.uploadcourse.MainActivity;
import com.jitc.adminjitc.uploadcourse.ViewCourseActivity;
import com.jitc.adminjitc.video.UploadVideoActivity;
import com.jitc.adminjitc.video.ViewVideoActivity;

public class MenuActivity extends AppCompatActivity {

    ImageView note,video,logout,pendaftar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        note = findViewById(R.id.note);
        video = findViewById(R.id.video);
        pendaftar = findViewById(R.id.pendaftar);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            sharedPrefManager.saveIsLogin(false);
            finishAffinity();
            startActivity(i);
        });

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ViewCourseActivity.class);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ViewVideoActivity.class);
                startActivity(intent);
            }
        });
        pendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListPendaftarActivity.class);
                startActivity(intent);
            }
        });
    }
}