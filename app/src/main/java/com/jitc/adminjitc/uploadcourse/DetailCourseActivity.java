package com.jitc.adminjitc.uploadcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jitc.adminjitc.R;
import com.squareup.picasso.Picasso;

public class DetailCourseActivity extends AppCompatActivity {

    ImageView cimage;
    TextView ctitle;
    TextView charga;
    TextView detaildeskripsi;
    TextView cdurasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cimage = findViewById(R.id.imagess);
        ctitle = findViewById(R.id.dnamacourse);
        charga = findViewById(R.id.dharga);
        cdurasi = findViewById(R.id.ddurasi);
        detaildeskripsi = findViewById(R.id.textdes);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String harga = intent.getStringExtra("harga");
        String durasi = intent.getStringExtra("durasi");
        String deskripsidetail = intent.getStringExtra("descdetail");

        String Url =  intent.getStringExtra("imagess");
//        Picasso.get().load(Url).into(cimage);
        if (Url.isEmpty()) {
            cimage.setImageResource(R.drawable.logojitc);
        } else{
            Picasso.get().load(Url).into(cimage);
        }
        ctitle.setText(title);
        charga.setText(harga);
        cdurasi.setText(durasi);
        detaildeskripsi.setText(deskripsidetail);
    }
}