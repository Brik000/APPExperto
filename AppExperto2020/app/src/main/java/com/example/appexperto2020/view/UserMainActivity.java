package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.ExpertAdapter;
import com.example.appexperto2020.control.UserMainController;

import lombok.Getter;

public class UserMainActivity extends AppCompatActivity {

    private UserMainController controller;
    private TextView welcomeTV;
    private ImageView logOutIV, profileIV, servicesIV;
    private RecyclerView expertsRV;
    @Getter
    private ExpertAdapter adapter;
    @Getter
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_main);
        welcomeTV = findViewById(R.id.welcomeTV);
        logOutIV = findViewById(R.id.logOutIV);
        profileIV = findViewById(R.id.profileiv);
        servicesIV = findViewById(R.id.servicesIV);
        expertsRV = findViewById(R.id.expertsRV);
        progressBar = findViewById(R.id.progressBarList);
        controller = new UserMainController(this);
        adapter = new ExpertAdapter(controller);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        getExpertsRV().setLayoutManager(linearLayoutManager);
        getExpertsRV().setAdapter(adapter);
    }

    public TextView getWelcomeTV() {
        return welcomeTV;
    }

    public ImageView getLogOutIV() {
        return logOutIV;
    }

    public ImageView getProfileIV() {
        return profileIV;
    }

    public ImageView getServicesIV() {
        return servicesIV;
    }

    public RecyclerView getExpertsRV() {
        return expertsRV;
    }

    @Override
    public void onBackPressed() {
        controller.logOutDialog();
    }
}
