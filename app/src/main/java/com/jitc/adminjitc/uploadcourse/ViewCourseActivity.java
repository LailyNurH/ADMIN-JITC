package com.jitc.adminjitc.uploadcourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.uploadcourse.model.UploadCourseData;

import java.util.ArrayList;

public class ViewCourseActivity extends AppCompatActivity {

    private RecyclerView viewCourseRV;
    private ProgressBar progressBar;
    private ArrayList <UploadCourseData> list = new ArrayList<>();
    private CourseAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewCourseRV = findViewById(R.id.viewCourseRV);
        progressBar = findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Note");

        viewCourseRV.setLayoutManager(new LinearLayoutManager(this));
        viewCourseRV.setHasFixedSize(true);

        getCourse();
//        showRecyclerList();
    }


    private void getCourse() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    UploadCourseData data = snapshot.getValue(UploadCourseData.class);
                    list.add(data);
                }

                adapter= new CourseAdapter(ViewCourseActivity.this,list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                viewCourseRV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ViewCourseActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}