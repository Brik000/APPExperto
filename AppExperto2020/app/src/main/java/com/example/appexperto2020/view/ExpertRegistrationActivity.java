package com.example.appexperto2020.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ExpertRegistrationController;
import com.example.appexperto2020.util.MultiSelectionSpinner;

public class ExpertRegistrationActivity extends AppCompatActivity {

    private ExpertRegistrationController controller;
    private EditText nameExpertET;
    private EditText idET;
    private EditText celularET;
    private EditText emailET;
    private EditText descriptionET;
    private ImageView addPhotoBut;
    private EditText passwordET;
    private GridView photoList;
    private Button registerBut;
    private MultiSelectionSpinner  jobSpinner;

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
        nameExpertET = findViewById(R.id.nameExpertET);
        passwordET = findViewById(R.id.passwordET);
        idET = findViewById(R.id.idET);
        celularET = findViewById(R.id.celularET);
        emailET = findViewById(R.id.emailET);
        addPhotoBut = findViewById(R.id.addPhotoBut);
        photoList = findViewById(R.id.photoList);
        registerBut = findViewById(R.id.registerBut);
        descriptionET = findViewById(R.id.descriptionET);
        jobSpinner = findViewById(R.id.jobSpinner);
        controller = new ExpertRegistrationController(this);
    }

    public EditText getPasswordET() {
        return passwordET;
    }

    public EditText getNameExpertET() {
        return nameExpertET;
    }

    public EditText getIdET() {
        return idET;
    }

    public EditText getCelularET() {
        return celularET;
    }

    public EditText getEmailET() {
        return emailET;
    }

    public EditText getDescriptionET() {
        return descriptionET;
    }

    public Button getRegisterBut() {
        return registerBut;
    }

    public MultiSelectionSpinner getJobSpinner() {
        return jobSpinner;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode, resultCode, data);
    }

}
