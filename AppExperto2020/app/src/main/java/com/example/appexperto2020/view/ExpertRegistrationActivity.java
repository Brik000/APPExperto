package com.example.appexperto2020.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ExpertRegistrationController;

public class ExpertRegistrationActivity extends AppCompatActivity {


    private ExpertRegistrationController controller;
    private EditText nameText;
    private EditText cedulaText;
    private EditText celularText;
    private EditText emailText;
    private ListView jobList;
    private TextView addJobTxt;
    private ImageView addPhotoBut;
    private GridView photoList;
    private Button registerBut;

    public ImageView getAddPhotoBut() {
        return addPhotoBut;
    }

    public GridView getPhotoList() {
        return photoList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_registration);
        nameText = findViewById(R.id.nameText);
        cedulaText = findViewById(R.id.cedulaText);
        celularText = findViewById(R.id.celularText);
        emailText =  findViewById(R.id.emailText);
        jobList = findViewById(R.id.jobList);
        addJobTxt = findViewById(R.id.addJobText);
        addPhotoBut = findViewById(R.id.addPhotoBut);
        photoList = findViewById(R.id.photoList);
        registerBut = findViewById(R.id.registerBut);
        controller = new ExpertRegistrationController(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode,resultCode,data);
    }

    public Button getRegisterBut() { return registerBut; }

    public EditText getNameText() {
        return nameText;
    }

    public EditText getCedulaText() {
        return cedulaText;
    }

    public EditText getCelularText() {
        return celularText;
    }

    public EditText getEmailText() {
        return emailText;
    }

    public ListView getJobList() {
        return jobList;
    }

    public TextView getAddJobTxt() { return addJobTxt;}
}
