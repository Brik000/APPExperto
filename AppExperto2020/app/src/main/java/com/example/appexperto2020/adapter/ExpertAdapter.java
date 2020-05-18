package com.example.appexperto2020.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.UserMainController;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.util.Constants;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {

    private ArrayList<Expert> experts;
    private UserMainController controller;
    private View.OnClickListener clickListener;

    public ExpertAdapter(UserMainController controller)
    {
        this.controller = controller;
        this.experts = new ArrayList<>();
    }

    public void setData(ArrayList<Expert> experts)
    {
        this.experts = experts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user, parent, false);
        ViewHolder holder = new ViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                clickListener.onClick(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expert expert = experts.get(position);
        //Optimizar el render de la imagen con el caché
        //cargar imagen
        holder.expertNameTV.setText(expert.getFirstName()+" "+expert.getLastName());
        HashMap<String, Job> jobs = expert.getJobList();
        String jobDescription = "";
        if(jobs != null)
        for(Job job : jobs.values())
        {
            jobDescription += job.getName() + " ";
        }
        holder.expertJobTV.setText(jobDescription);
        FirebaseStorage storage = FirebaseStorage.getInstance();
//        storage.getReference().child(Constants.FOLDER_PROFILE_PICTURES).child(expert.getId()).getDownloadUrl().
//                addOnSuccessListener(
//                        uri ->{
//                            Log.e(">>>>","PP SUCCESSFULLY DOWNLOAD");
//                        }
//                );
        holder.goToBtn.setContentDescription(expert.getId());
        //IMPLEMENTAR EL DETALLE DEL EXPERTO
        holder.goToBtn.setOnClickListener(controller);
    }

    @Override
    public int getItemCount() {
        return experts.size();
    }

    public Expert getItem(int position){
        return experts.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private View view;
        private ImageView expertIV;
        private TextView expertNameTV;
        private TextView expertJobTV;
        private ImageView goToBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            expertIV = view.findViewById(R.id.expertIV);
            expertNameTV = view.findViewById(R.id.expertNameTV);
            expertJobTV = view.findViewById(R.id.expertJobTV);
            goToBtn = view.findViewById(R.id.goToBtn);

        }
    }
}
