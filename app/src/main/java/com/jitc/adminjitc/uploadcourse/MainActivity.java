package com.jitc.adminjitc.uploadcourse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.UI.MenuActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private CardView addImage;
    private ImageView courseImage;
    private ImageView preview;
    private EditText judulCourse;
    private EditText hargaCourse;
    private EditText durasiCourse;
    private EditText deskripsicourse;
    private Button uploadPelatihanBtn;
    private LinearLayout linearimg;

    private final  int REQ = 1;
    private Bitmap bitmap;
    private DatabaseReference reference;
    private StorageReference storageReference;
    String dowloadUrl = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pd = new ProgressDialog(this);

        linearimg = findViewById(R.id.linearImg);
        addImage = findViewById(R.id.addImage);
        preview = findViewById(R.id.imgpreview);
        courseImage = findViewById(R.id.imagepreview);
        judulCourse = findViewById(R.id.judulCourse);
        hargaCourse = findViewById(R.id.hargaCourse);
        durasiCourse = findViewById(R.id.durasiCourse);
        deskripsicourse = findViewById(R.id.deskripsi);
        uploadPelatihanBtn = findViewById(R.id.uploadCourseBtn);

        addImage.setOnClickListener(v -> openGallery());
        uploadPelatihanBtn.setOnClickListener(v -> {
                if(judulCourse.getText().toString().isEmpty() || hargaCourse.getText().toString().isEmpty()||durasiCourse.getText().toString().isEmpty()||deskripsicourse.getText().toString().isEmpty()){
                    judulCourse.setError("Wajib Terisi");
                    hargaCourse.setError("Wajib Terisi");
                    durasiCourse.setError("Wajib Terisi");
                    deskripsicourse.setError("Wajib Terisi");
                    judulCourse.requestFocus();
                    hargaCourse.requestFocus();
                    durasiCourse.requestFocus();
                    deskripsicourse.requestFocus();
                }
                else if (bitmap == null){
                    uploadData();
                }else {
                    uploadImage();
                }
            });
        }
    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Pelatihan").child(finalImg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(MainActivity.this, task -> {
            if (task.isSuccessful()){
                uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    dowloadUrl = String.valueOf(uri);
                    uploadData();
                }));
            } else {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "Ada sesuatu hal yang salah", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void uploadData() {
        if (bitmap==null){
            Toast.makeText(MainActivity.this, "Gambar Wajib Terisi", Toast.LENGTH_SHORT).show();
        }else{
        reference = reference.child("Pelatihan");
        final String uniqueKey = reference.push().getKey();

        String title =  judulCourse.getText().toString();
        String harga =  hargaCourse.getText().toString();
        String durasi =  durasiCourse.getText().toString();
        String deskripsi =  deskripsicourse.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

//        UploadCourseData uploadCourseData = new UploadCourseData(title,harga,durasi,deskripsi,dowloadUrl,date,time,uniqueKey);
        UploadCourseData uploadCourseData = new UploadCourseData(title,durasi,harga,deskripsi,dowloadUrl,date,time,uniqueKey);

        reference.child(uniqueKey).setValue(uploadCourseData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "Pelatihan Berhasil di upload", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ViewCourseActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "Ada sesuatu hal yang Salah", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }

    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");


        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, REQ);
//        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pickimage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQ && resultCode == RESULT_OK);{
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            linearimg.setVisibility(View.GONE);
            preview.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(MainActivity.this, MenuActivity.class));

    }

}