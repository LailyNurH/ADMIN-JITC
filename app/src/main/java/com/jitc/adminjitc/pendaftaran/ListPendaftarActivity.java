package com.jitc.adminjitc.pendaftaran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jitc.adminjitc.R;

import java.util.ArrayList;

public class ListPendaftarActivity extends AppCompatActivity {
    private RecyclerView listpendaftarRV;
    private ArrayList<Pendaftaran> listdaftar = new ArrayList<>();
    private PendaftarAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pendaftar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listpendaftarRV = findViewById(R.id.rvpendaftar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pendaftar");

        listpendaftarRV.setLayoutManager(new LinearLayoutManager(this));
        listpendaftarRV.setHasFixedSize(true);
        listpendaftarRV.setNestedScrollingEnabled(false);
        getData();
//        showRecyclerList();
    }


    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                listdaftar = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Pendaftaran data = snapshot.getValue(Pendaftaran.class);
                    listdaftar.add(data);
                }

                adapter= new PendaftarAdapter(ListPendaftarActivity.this,listdaftar);
                adapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
                listpendaftarRV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(ListPendaftarActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}