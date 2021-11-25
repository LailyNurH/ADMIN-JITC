package com.jitc.adminjitc.video;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.UI.MenuActivity;
import com.jitc.adminjitc.uploadcourse.UpdateCourseActivity;

import java.util.HashMap;

public class UpdateVideoActivity extends AppCompatActivity {
    private EditText etTitleVideo;
    private VideoView updateVideoView;
    private Button updateBtnVideo;
    private FloatingActionButton pickVideo;
    MediaController mc;

    private static final int VIDEO_PICK_CODE = 101;

    private Uri uri = null;
    private String uptitle, upvideo;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_video);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Menunggu");
        progressDialog.setMessage("Prosses Unggah Video");
        progressDialog.setCanceledOnTouchOutside(false);

        etTitleVideo = findViewById(R.id.etTitlevideo);
        updateVideoView = findViewById(R.id.updateVideoView);
        updateBtnVideo = findViewById(R.id.updateVideoBtn);
        pickVideo = findViewById(R.id.pickVideoBtn);

        uptitle = getIntent().getStringExtra("title");
        upvideo = getIntent().getStringExtra("videoUrl");

        etTitleVideo.setText(uptitle);
        pickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryVideo();
            }


            private void openGalleryVideo() {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), VIDEO_PICK_CODE);
            }
        });
        updateBtnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uptitle = etTitleVideo.getText().toString().trim();
                if (TextUtils.isEmpty(uptitle)) {
                    Toast.makeText(UpdateVideoActivity.this, "Judul Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (uri == null) {
                    Toast.makeText(UpdateVideoActivity.this, "Pilih Video sebelum di tambahkan", Toast.LENGTH_SHORT).show();
                } else {
                    updateVideoFirebase("");
                }

            }
        });
    }

    private void updateVideoFirebase(String v) {
        progressDialog.show();
        String timestamp = "" + System.currentTimeMillis();

        String filePathAndName = "Videos/" + "video_" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                  ;
                            hashMap.put("title", "" + etTitleVideo.getText().toString());
                            hashMap.put("timestamp", "" + timestamp);
                            hashMap.put("videoUrl", "" + downloadUri);
                            String id = getIntent().getStringExtra("id");
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Videos");

//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
                            reference.child(id).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UpdateVideoActivity.this, "Video berhasil di upload", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UpdateVideoActivity.this, ViewVideoActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(UpdateVideoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateVideoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_PICK_CODE) {
                if (data != null) {
                    uri = data.getData();
                }
                setVideoToVideoView();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setVideoToVideoView() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(updateVideoView);

        updateVideoView.setVideoURI(uri);
        updateVideoView.requestFocus();
        updateVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                updateVideoView.pause();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
