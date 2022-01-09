package com.jitc.adminjitc.uploadcourse;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jitc.adminjitc.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewAdapter> {
    private Context context;
    private final ArrayList <UploadCourseData>list;
    public static ImageView loadimg;
    public static Button pickimg;
    public static int PICK_IMAGE_CODE = 22;
    public static Uri filepath;
    public static ProgressDialog pDialog;
    public CourseAdapter(Context context, ArrayList<UploadCourseData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list_item,parent,false);
        return new CourseViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewAdapter holder, int position) {
        holder.getAdapterPosition();
        UploadCourseData currentItem = list.get(position);
        holder.coursejudul.setText(currentItem.getTitle());
        holder.courseharga.setText(currentItem.getHarga());
        holder.coursedurasi.setText(currentItem.getDurasi());
        holder.deskripsic.setText(currentItem.getDeskripsi());
        try{
            if (currentItem.getImage()!=null)
                Picasso.get().load(currentItem.getImage()).into(holder.courseimage);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.updateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateCourseActivity.class);
                intent.putExtra("title",currentItem.getTitle());
                intent.putExtra("durasi",currentItem.getDurasi());
                intent.putExtra("deskripsi",currentItem.getDeskripsi());
                intent.putExtra("harga",currentItem.getHarga());
                intent.putExtra("image",currentItem.getImage());
                intent.putExtra("key",currentItem.getKey());

                context.startActivity(intent);
            }
        });

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailCourseActivity.class);

                intent.putExtra("title", currentItem.getTitle());
                intent.putExtra("harga", currentItem.getHarga());
                intent.putExtra("durasi", currentItem.getDurasi());
                intent.putExtra("descdetail", currentItem.getDeskripsi());
                intent.putExtra("imagess", currentItem.getImage());
                context.startActivity(intent);
            }
        });

        holder.deletecourse.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Apakah Anda yakin ingin menghapus data pelatihan ini? ");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pelatihan");
                            reference.child(currentItem.getKey()).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Data Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                            notifyItemRemoved(position);
                        }
                    }
            );
            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();

                        }
                    }
            );
            AlertDialog dialog = null;
            try {
                dialog = builder.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dialog !=null)
                dialog.show();

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CourseViewAdapter extends RecyclerView.ViewHolder {
        private ImageButton deletecourse;
        private ImageButton updateCourse;
        private TextView coursejudul;
        private TextView coursedurasi;
        private TextView courseharga;
        private TextView deskripsic;
        private ImageView courseimage;

        View v;

        public CourseViewAdapter(@NonNull  View itemView) {
            super(itemView);

            deletecourse = itemView.findViewById(R.id.deleteNote);
            coursejudul = itemView.findViewById(R.id.tittlecourse);
            coursedurasi = itemView.findViewById(R.id.durasi_Course);
            courseharga = itemView.findViewById(R.id.harga_course);
            courseimage = itemView.findViewById(R.id.courseimage);
            deskripsic = itemView.findViewById(R.id.deskripsicourse);
            updateCourse= itemView.findViewById(R.id.updateCourse);

            v=itemView;
        }
    }
}
