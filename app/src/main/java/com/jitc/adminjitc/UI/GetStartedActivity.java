package com.jitc.adminjitc.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.jitc.adminjitc.R;

public class GetStartedActivity extends AppCompatActivity {
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        imageButton = findViewById(R.id.btngetstarted);
//        imageButton.setOnClickListener(v -> {
//            Intent intent = new Intent(GetStartedActivity.this, MenuActivity.class);
//            startActivity(intent);
//        });

    }
}