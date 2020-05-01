package com.example.appexperto2020.adapter;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {

    private ArrayList<Expert> experts;
    private UserMainController controller;
    private View.OnClickListener clickListener;

    public ExpertAdapter(ArrayList<Expert> experts, UserMainController controller)
    {
        this.controller = controller;
        this.experts = experts;
    }

    public void setOnClickListener(View.OnClickListener callback)
    {
        this.clickListener = (View.OnClickListener) callback;
    }

    public void setData(ArrayList<Expert> experts)
    {
        this.experts = experts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expert_fragement, parent, false);
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
        holder.custom(experts.get(position), controller);
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

        public void custom( Expert expert, UserMainController controller)
        {
            //expertIV.
            expertNameTV.setText(expert.getUsername());
            expertJobTV.setText(expert.getJobs().toString());
            goToBtn.setContentDescription(expert.getId());
            //IMPLEMENTAR EL DETALLE DEL EXPERTO
            goToBtn.setOnClickListener(controller);

        }
    }
}
