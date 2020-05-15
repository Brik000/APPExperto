package com.example.appexperto2020.view;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ExpertRegistrationController;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.MultiSelectionSpinner;
import com.google.android.material.textfield.TextInputLayout;

public class ExpertRegistrationActivity extends AppCompatActivity {

    private ExpertRegistrationController controller;

    private ImageView sessionImage;
    private TextView iAmTV;

    private TextInputLayout nameExpertET;
    private TextInputLayout lastNameExpertET;
    private TextInputLayout documentET;
    private TextInputLayout celularET;
    private TextInputLayout emailET;
    private TextInputLayout descriptionET;
    private ImageView addPhotoBut;
    private TextInputLayout passwordET;
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
        String response = getIntent().getExtras().getString(Constants.SESSION_TYPE);
        nameExpertET = findViewById(R.id.firstNameET);
        passwordET = findViewById(R.id.passwordET);
        documentET = findViewById(R.id.documentET);
        celularET = findViewById(R.id.cellphoneET);
        emailET = findViewById(R.id.mailET);
        addPhotoBut = findViewById(R.id.addPhotoBut);
        photoList = findViewById(R.id.photoList);
        registerBut = findViewById(R.id.registerBut);
        descriptionET = findViewById(R.id.descriptionET);
        jobSpinner = findViewById(R.id.jobSpinner);
        controller = new ExpertRegistrationController(this);

        sessionImage = findViewById(R.id.sessionImage);
        iAmTV = findViewById(R.id.iAmTV);
        if(response.equals(Constants.SESSION_CLIENT)) {
            sessionImage.setImageResource(R.drawable.client);
            iAmTV.setText(getString(R.string.register_client_title));
        }
        else {
            sessionImage.setImageResource(R.drawable.worker);
            iAmTV.setText(getString(R.string.register_expert_title));
        }
    }

    public EditText getPasswordET() {
        return passwordET.getEditText();
    }

    public EditText getNameExpertET() {
        return nameExpertET.getEditText();
    }

    public EditText getDocumentET() {
        return documentET.getEditText();
    }

    public EditText getCelularET() {
        return celularET.getEditText();
    }

    public EditText getEmailET() {
        return emailET.getEditText();
    }

    public EditText getDescriptionET() {
        return descriptionET.getEditText();
    }

    public Button getRegisterBut() {
        return registerBut;
    }

    public MultiSelectionSpinner getJobSpinner() {
        return jobSpinner;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode, resultCode, data);
    }

}
