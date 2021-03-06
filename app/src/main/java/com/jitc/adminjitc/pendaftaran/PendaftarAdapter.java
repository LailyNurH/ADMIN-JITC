package com.jitc.adminjitc.pendaftaran;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jitc.adminjitc.R;
import com.jitc.adminjitc.uploadcourse.DetailCourseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PendaftarAdapter extends RecyclerView.Adapter<PendaftarAdapter.PendaftarViewHolder> {

    private Context context;
    private List<Pendaftaran> list;

    public PendaftarAdapter(Context context, List<Pendaftaran> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PendaftarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_pendaftar, parent, false);
        return new PendaftarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendaftarViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Pendaftaran pendaftaran = list.get(position);

        holder.namapendaftar.setText(pendaftaran.getNama());
//        holder.jkpendaftar.setText(pendaftaran.getJeniskelamin());
//        holder.medsos.setText(pendaftaran.getMedsos());
//        holder.alamatemail.setText(pendaftaran.getEmail());
//        holder.ket.setText(pendaftaran.getKeterangan());
        holder.nohp.setText(pendaftaran.getNohp());
        holder.corsedipilih.setText(pendaftaran.getCourse());
//        holder.asalinstansi.setText(pendaftaran.getAsalkampus());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPendaftarActivity.class);

                intent.putExtra("namapendaftar", pendaftaran.getNama());
                intent.putExtra("emailpendaftar", pendaftaran.getEmail());
                intent.putExtra("instansipendaftar", pendaftaran.getAsalkampus());
                intent.putExtra("pelatihanpendaftar", pendaftaran.getCourse());
                intent.putExtra("sosmedpendaftar", pendaftaran.getMedsos());
                intent.putExtra("gambarpendaftar", pendaftaran.getImage());
                intent.putExtra("keteranganpendaftar", pendaftaran.getKeterangan());
                intent.putExtra("nomorhape", pendaftaran.getNohp());
                context.startActivity(intent);
            }
        });
        try{
            if (pendaftaran.getImage()!=null)
                Glide.with(context)
                        .load(pendaftaran.getImage())
                        .circleCrop()
                        .into(holder.imgpendaftar);
//                Picasso.get().load(pendaftaran.getImage()).into(holder.imgpendaftar);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.hubungiPendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri url = Uri.parse("https://api.whatsapp.com/send/?phone=62&text&app_absent=0" + pendaftaran.getNohp()); // get your url from list item or your code.
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                context.startActivity(intent);
            }
        });
        holder.deletePendaftar.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Apakah Anda Yakin Ingin Menghapus Pendaftar ? ");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Pendaftar");
                            reference.child(pendaftaran.getKey()).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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

    public static class PendaftarViewHolder extends RecyclerView.ViewHolder {

        private final TextView namapendaftar;
        private TextView alamatemail;
        private TextView asalinstansi;
        private TextView nohp;
        private TextView medsos;
        private TextView ket;
        private TextView corsedipilih;
        private ImageView imgpendaftar;
        private ImageButton deletePendaftar;
        private ImageButton hubungiPendaftar;
        View view;

        public PendaftarViewHolder(@NonNull View itemView) {
            super(itemView);

            namapendaftar = itemView.findViewById(R.id.namapendaftar);
//            ket= itemView.findViewById(R.id.keterangancourse);
//            medsos= itemView.findViewById(R.id.medsos);
//            alamatemail = itemView.findViewById(R.id.alamatEmail);
//            asalinstansi = itemView.findViewById(R.id.asalinstansi);
            nohp = itemView.findViewById(R.id.nohp);
            corsedipilih = itemView.findViewById(R.id.coursdipilih);
            imgpendaftar = itemView.findViewById(R.id.imgpendaftar);
            deletePendaftar = itemView.findViewById(R.id.deletePendaftar);
            hubungiPendaftar = itemView.findViewById(R.id.hubungiPendaftar);

            view=itemView;
        }
    }
}
