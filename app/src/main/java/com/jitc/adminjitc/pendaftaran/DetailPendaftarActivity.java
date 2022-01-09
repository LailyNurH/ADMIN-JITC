package com.jitc.adminjitc.pendaftaran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jitc.adminjitc.R;
import com.squareup.picasso.Picasso;

public class DetailPendaftarActivity extends AppCompatActivity {
    ImageView gambarpendaftar;
    Button hubpendaftar;
    TextView namapendaftar, pendaftarNoHp,emailpendaftar, instansipendaftar, pelatihanpendaftar, medsospendaftar, keteranganpendaftar,nohape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pendaftar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gambarpendaftar = findViewById(R.id.imagependaftar);
        namapendaftar = findViewById(R.id.pendaftarNama);
        emailpendaftar = findViewById(R.id.pendaftarEmail);
        instansipendaftar = findViewById(R.id.pendaftarInstansi);
        pelatihanpendaftar = findViewById(R.id.pendaftarPelatihan);
        medsospendaftar = findViewById(R.id.pendaftarSosmed);
        pendaftarNoHp = findViewById(R.id.pendaftarNoHp);
        keteranganpendaftar = findViewById(R.id.pendaftarKeterangan);
        hubpendaftar = findViewById(R.id.pendaftarhubungi);

        Intent intent = getIntent();
        String npendaftar = intent.getStringExtra("namapendaftar");
        String ependaftar = intent.getStringExtra("emailpendaftar");
        String ppelatihan = intent.getStringExtra("pelatihanpendaftar");
        String sosmedp = intent.getStringExtra("sosmedpendaftar");
        String instansi = intent.getStringExtra("instansipendaftar");
        String keterangan = intent.getStringExtra("keteranganpendaftar");
        String nhape = intent.getStringExtra("nomorhape");

        String imgp = intent.getStringExtra("gambarpendaftar");
        if (imgp.isEmpty()) {
            gambarpendaftar.setImageResource(R.drawable.logojitc);
        } else {
            Picasso.get().load(imgp).into(gambarpendaftar);
        }


        namapendaftar.setText(npendaftar);
        pendaftarNoHp.setText(nhape);
        emailpendaftar.setText(ependaftar);
        pelatihanpendaftar.setText(ppelatihan);
        medsospendaftar.setText(sosmedp);
        instansipendaftar.setText(instansi);
        keteranganpendaftar.setText(keterangan);
        hubpendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomorhapee = getIntent().getStringExtra(String.valueOf(nohape));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + nomorhapee));
                startActivity(intent);

            }
        });
    }
}