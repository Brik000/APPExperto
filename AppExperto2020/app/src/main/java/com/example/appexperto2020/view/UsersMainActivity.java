package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.UserMainController;

public class UsersMainActivity extends AppCompatActivity {

    private UserMainController controller;
    private TextView welcomeTV;
    private ImageView logOutIV, profileIV, servicesIV;
    private RecyclerView expertsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_main);
        welcomeTV = findViewById(R.id.welcomeTV);
        logOutIV = findViewById(R.id.logOutIV);
        profileIV = findViewById(R.id.profileiv);
        servicesIV = findViewById(R.id.servicesIV);
        expertsRV = findViewById(R.id.expertsRV);

        controller = new UserMainController(this);

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
}
