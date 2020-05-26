package com.example.appexperto2020.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.UserMainController;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {

    private ArrayList<Expert> experts;
    private UserMainController controller;

    public ExpertAdapter(UserMainController controller)
    {
        Log.e("", "INIT");
        this.controller = controller;
        experts = new ArrayList<>();
    }

    public void setData(ArrayList<Expert> experts)
    {
        this.experts = experts;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("", "VIEW HOLDER");

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_expert, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    public Expert getItem(int position)
    {
        return experts.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("", "BIND");

        holder.custom(experts.get(position));
        holder.goToBtn.setContentDescription(experts.get(position).getId());
        holder.goToBtn.setOnClickListener(controller);
    }

    @Override
    public int getItemCount() {

        return experts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView expertNameTv, expertJobTV;
        private ImageView expertIV, goToBtn;
        private View view;

        public ViewHolder(View view) {
            super(view);
            Log.e("", "VIEW HOLDER");

            this. view = view;
            expertIV = view.findViewById(R.id.expertIV);
            expertJobTV = view.findViewById(R.id.expertJobTV);
            expertNameTv = view.findViewById(R.id.expertNameTV);
            goToBtn = view.findViewById(R.id.goToBtn);
            view.setTag(this);

        }


        // Personalizamos un ViewHolder a partir de un lugar
        public void custom(Expert expert) {
            Log.e("", "CUSTOM");
            expertNameTv.setText(expert.getFirstName() + " " + expert.getLastName());
            String jobs = "";
            if(expert.getJobList() != null) {
                Object[] keySet = expert.getJobList().keySet().toArray();
                for (int i = 0; i < keySet.length; i++) {
                    Job j = expert.getJobList().get(keySet[i]);
                    if (jobs.equals("")) {
                        jobs += " " + j.getName();
                    } else {
                        jobs += " || " + j.getName();
                    }
                }
                expertJobTV.setText(jobs);
            }
            File imageFile = new File(view.getContext().getExternalFilesDir(null)+"/"+expert.getId());
            if(imageFile.exists())
            {
                loadImage(expertIV, imageFile);
                Log.e("-------", "image exists");
            }else{

                FirebaseStorage storage = FirebaseStorage.getInstance();
                Log.e("-------", "image doesnt exists");

                storage.getReference().child("profilePictures").child("-M7pd7jmsrLHTKIn9YHj").getDownloadUrl().
                        addOnSuccessListener(
                                uri ->{

                                    File file = new File(view.getContext().getExternalFilesDir(null) +"/"+expert.getId());
                                    new Thread(
                                            () ->
                                            {
                                                HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                                                utilDomi.saveURLImageOnFile(uri.toString(), file);
                                                Log.e("---->","se guarda");
                                                loadImage(expertIV, imageFile);
                                            }
                                    ).start();
                                }
                        );
            }
        }



        private void loadImage(ImageView expertIV, File file) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
            expertIV.setImageBitmap(bitmap);
        }
    }


    public void addExpert(Expert ex)
    {
        experts.add(ex);
        notifyDataSetChanged();
    }
}
