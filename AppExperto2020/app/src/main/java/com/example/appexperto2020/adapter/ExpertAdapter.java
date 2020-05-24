package com.example.appexperto2020.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;

import java.util.ArrayList;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {

    private ArrayList<Expert> experts;
    private View.OnClickListener mClickListener;


    public ExpertAdapter()
    {
        Log.e("", "INIT");

        experts = new ArrayList<>();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = (View.OnClickListener) callback;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
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
    }

    @Override
    public int getItemCount() {

        return experts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView expertNameTv, expertJobTV;
        private ImageView expertIV;
        private View view;

        public ViewHolder(View view) {
            super(view);
            Log.e("", "VIEW HOLDER");

            this. view = view;
            expertIV = view.findViewById(R.id.expertIV);
            expertJobTV = view.findViewById(R.id.expertJobTV);
            expertNameTv = view.findViewById(R.id.expertNameTV);
            view.setTag(this);

        }


        // Personalizamos un ViewHolder a partir de un lugar
        public void custom(Expert expert) {
            Log.e("", "CUSTOM");
            expertNameTv.setText(expert.getFirstName() + " " + expert.getLastName());
            String jobs = "";
            Object[] keySet = expert.getJobList().keySet().toArray();
            for(int i = 0; i<keySet.length;i++){
                Job j = expert.getJobList().get(keySet[i]);
                if(jobs.equals(""))
                {
                    jobs += " "+j.getName();
                }else
                {
                    jobs += " || "+j.getName();

                }
            }
            expertJobTV.setText(jobs);
        }
    }
    public void addExpert(Expert ex)
    {
        Log.e("", "EXO ADD");

        experts.add(ex);

        notifyDataSetChanged();
    }
}
