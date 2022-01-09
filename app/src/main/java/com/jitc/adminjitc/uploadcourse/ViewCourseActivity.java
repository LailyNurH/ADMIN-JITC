package com.jitc.adminjitc.uploadcourse;

import static com.jitc.adminjitc.uploadcourse.CourseAdapter.PICK_IMAGE_CODE;
import static com.jitc.adminjitc.uploadcourse.CourseAdapter.filepath;
import static com.jitc.adminjitc.uploadcourse.CourseAdapter.loadimg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.UI.MenuActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewCourseActivity extends AppCompatActivity {

    private RecyclerView viewCourseRV;
    private ProgressBar progressBar;
    private ArrayList <UploadCourseData> list = new ArrayList<>();
    private CourseAdapter adapter;
    private FloatingActionButton fabPelatihan;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewCourseRV = findViewById(R.id.viewCourseRV);
        progressBar = findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Pelatihan");

        fabPelatihan = findViewById(R.id.addpelatihan);
        fabPelatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCourseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==    PICK_IMAGE_CODE&& resultCode ==RESULT_OK && data !=null && data.getData()!= null ){
            filepath = data.getData();
            Picasso.get().load(filepath).into(loadimg);
        }
    }

}