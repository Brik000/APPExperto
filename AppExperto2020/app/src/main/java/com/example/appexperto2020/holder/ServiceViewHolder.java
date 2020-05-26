package com.example.appexperto2020.holder;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appexperto2020.R;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceViewHolder extends RecyclerView.ViewHolder{

    private TextView jobServiceTV;
    private TextView statusServiceTV;
    private TextView userServiceTV;
    private TextView bodyServiceTV;
    private Button chatServiceBtn;
    private CircleImageView serviceCV;
    private ConstraintLayout serviceContainerCL;
    private ConstraintLayout headerServiceCL;

    public ServiceViewHolder(@NonNull View itemView) {
        super(itemView);
        jobServiceTV = itemView.findViewById(R.id.jobServiceTV);
        statusServiceTV = itemView.findViewById(R.id.statusServiceTV);
        userServiceTV = itemView.findViewById(R.id.userServiceTV);
        bodyServiceTV = itemView.findViewById(R.id.bodyServiceTV);
        chatServiceBtn = itemView.findViewById(R.id.chatServiceBtn);
        serviceCV = itemView.findViewById(R.id.serviceCV);
        serviceContainerCL = itemView.findViewById(R.id.serviceContainerCL);
        headerServiceCL = itemView.findViewById(R.id.headerServiceCL);

    }
}
