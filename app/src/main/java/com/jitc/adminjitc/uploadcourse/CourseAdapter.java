package com.jitc.adminjitc.uploadcourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.uploadcourse.model.UploadCourseData;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewAdapter> {
    private Context context;
    private final ArrayList <UploadCourseData>list;

    public CourseAdapter(Context context, ArrayList<UploadCourseData> list) {
        this.context = context;
        this.list = list;
    }


//    public CourseAdapter(ViewCourseActivity viewCourseActivity, ArrayList<UploadCourseData> list) {
//    }


    @NonNull
    @Override
    public CourseAdapter.CourseViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list_item,parent,false);
        return new CourseViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewAdapter holder, int position) {
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
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.courseimage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_dialog))
                        .setExpanded(true,1100)
                        .create();
                View myview=dialogPlus.getHolderView();
                final EditText upnama=myview.findViewById(R.id.unama);
                final EditText upharga=myview.findViewById(R.id.uharga);
                final EditText updeskripsi=myview.findViewById(R.id.udeskripsi);
                final EditText updurasi=myview.findViewById(R.id.udurasi);
                final ImageView upimages= myview.findViewById(R.id.updateImage);
                Button update=myview.findViewById(R.id.btnUpdate);

                upnama.setText(currentItem.getTitle());
                upharga.setText(currentItem.getHarga());
                updeskripsi.setText(currentItem.getDeskripsi());
                updurasi.setText(currentItem.getDurasi());

//                upimages.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(pickimage,REQ);
//                    }
//                });
                dialogPlus.show();
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("title",upnama.getText().toString());
                        map.put("harga",upharga.getText().toString());
                        map.put("deskripsi",updeskripsi.getText().toString());
                        map.put("durasi",updurasi.getText().toString());
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Note");
                        reference.child(currentItem.getKey()).updateChildren(map)

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Course berhasil di Update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailCourseActivity.class);

                Toast.makeText(context, "Di Klik", Toast.LENGTH_SHORT).show();
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
            builder.setMessage("Are you sure to delete this note ? ");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Note");
                            reference.child(currentItem.getKey()).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
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
