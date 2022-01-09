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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.UI.MenuActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateCourseActivity extends AppCompatActivity {
    private ImageView updateImageCourse;
    private EditText updatejudulCourse, updatehargaCourse, updatedurasiCourse, updatedeskripsiCourse;
    private Button btnUpdateCourse;

    private String updatejudul, updatedeskripsi, updateharga, updatedurasi, updateimage;
    private final int REQ = 1;
    private Bitmap bitmap = null;

    private StorageReference storageReference;
    private DatabaseReference reference;

    private String dowloadUrl;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pd = new ProgressDialog(this);
        pd.setTitle("Mohon Menunggu");
        pd.setMessage("Proses Update Pelatihan");
        pd.setCanceledOnTouchOutside(false);

        updatejudul = getIntent().getStringExtra("title");
        updatedeskripsi = getIntent().getStringExtra("deskripsi");
        updateharga = getIntent().getStringExtra("harga");
        updatedurasi = getIntent().getStringExtra("durasi");
        updateimage = getIntent().getStringExtra("image");


        updateImageCourse = findViewById(R.id.updateImgCourse);
        updatejudulCourse = findViewById(R.id.updatejudulCourse);
        updatedeskripsiCourse = findViewById(R.id.updatedeskripsi);
        updatedurasiCourse = findViewById(R.id.updatedurasiCourse);
        updatehargaCourse = findViewById(R.id.updatehargaCourse);
        btnUpdateCourse = findViewById(R.id.updateCourseBtn);

        try {
            Picasso.get().load(updateimage).into(updateImageCourse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updatejudulCourse.setText(updatejudul);
        updatedeskripsiCourse.setText(updatedeskripsi);
        updatedurasiCourse.setText(updatedurasi);
        updatehargaCourse.setText(updateharga);

        reference = FirebaseDatabase.getInstance().getReference().child("Pelatihan");
        storageReference = FirebaseStorage.getInstance().getReference();

        updateImageCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatejudul = updatejudulCourse.getText().toString();
                updatedeskripsi = updatedeskripsiCourse.getText().toString();
                updatedurasi = updatedurasiCourse.getText().toString();
                updateharga = updatehargaCourse.getText().toString();
                checkValidation();

            }
        });
    }

    private void checkValidation() {
        if (updatejudul.isEmpty()) {
            updatejudulCourse.setError("Tidak Boleh Kosong");
            updatejudulCourse.requestFocus();
        } else if (updateharga.isEmpty()) {
            updatehargaCourse.setError("Tidak Boleh Kosong");
            updatehargaCourse.requestFocus();

        } else if (updatedeskripsi.isEmpty()) {
            updatedeskripsiCourse.setError("Tidak Boleh Kosong");
            updatedeskripsiCourse.requestFocus();

        } else if (updatedurasi.isEmpty()) {
            updatedurasiCourse.setError("Tidak Boleh Kosong");
            updatedurasiCourse.requestFocus();

        } else if (bitmap == null) {
            updateData("");
        } else {
            updateImage();
        }
    }

    private void updateData(String s) {
        if (bitmap==null){
            Toast.makeText(UpdateCourseActivity.this, "Gambar Wajib Terisi", Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            HashMap map = new HashMap();
            map.put("title", updatejudulCourse.getText().toString());
            map.put("harga", updatehargaCourse.getText().toString());
            map.put("deskripsi", updatedeskripsiCourse.getText().toString());
            map.put("durasi", updatedurasiCourse.getText().toString());
            map.put("image", s);

            String key = getIntent().getStringExtra("key");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pelatihan");

            reference.child(key).updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(UpdateCourseActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateCourseActivity.this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(UpdateCourseActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updateImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Pelatihan").child(finalImg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(UpdateCourseActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dowloadUrl = String.valueOf(uri);
                                    updateData(dowloadUrl);
                                }

                            });
                        }
                    });

                } else {

                    Toast.makeText(UpdateCourseActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");


        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQ && resultCode == RESULT_OK) ;
        {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateImageCourse.setImageBitmap(bitmap);
        }
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(UpdateCourseActivity.this, MenuActivity.class));

    }
}