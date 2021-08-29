package com.jitc.adminjitc.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.jitc.adminjitc.R;
import com.jitc.adminjitc.pendaftaran.ListPendaftarActivity;
import com.jitc.adminjitc.uploadcourse.MainActivity;
import com.jitc.adminjitc.uploadcourse.ViewCourseActivity;
import com.jitc.adminjitc.video.UploadVideoActivity;
import com.jitc.adminjitc.video.ViewVideoActivity;

public class MenuActivity extends AppCompatActivity {

    ImageView note,video;
            Button lihatvideo,liatcourse,listdaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        note = findViewById(R.id.note);
        video = findViewById(R.id.video);

        liatcourse = findViewById(R.id.lihatcourse);
        liatcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ViewCourseActivity.class);
                startActivity(intent);
            }
        });
        listdaftar = findViewById(R.id.pendaftar);
        listdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListPendaftarActivity.class);
                startActivity(intent);
            }
        });
        lihatvideo = findViewById(R.id.lihatvideo);
        lihatvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ViewVideoActivity.class);
                startActivity(intent);
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UploadVideoActivity.class);
                startActivity(intent);
            }
        });
    }
}