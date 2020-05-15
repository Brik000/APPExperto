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
import com.example.appexperto2020.control.RegisterController;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.MultiSelectionSpinner;
import com.google.android.material.textfield.TextInputLayout;

import lombok.Getter;

public class RegisterActivity extends AppCompatActivity {

    private RegisterController controller;
    @Getter
    private ImageView sessionImage;
    @Getter
    private TextView iAmTV, addFilesTV;

    private TextInputLayout fistNameET;
    private TextInputLayout lastNameET;
    private TextInputLayout documentET;
    private TextInputLayout celularET;
    private TextInputLayout emailET;
    private TextInputLayout descriptionET;
    private ImageView addPhotoBut;
    private TextInputLayout passwordET;
    private GridView photoList;
    private Button registerBut;
    private MultiSelectionSpinner  jobSpinner;
    private ImageView addPhotoIV;

    public ImageView getAddPhotoBut() {
        return addPhotoBut;
    }

    public GridView getPhotoList() {
        return photoList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String session = getIntent().getExtras().getString(Constants.SESSION_TYPE);
        fistNameET = findViewById(R.id.firstNameET);
        addPhotoIV = findViewById(R.id.addPhotoIV);
        fistNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        passwordET = findViewById(R.id.passwordET);
        documentET = findViewById(R.id.documentET);
        celularET = findViewById(R.id.cellphoneET);
        emailET = findViewById(R.id.mailET);
        addPhotoBut = findViewById(R.id.addPhotoBut);
        photoList = findViewById(R.id.photoList);
        registerBut = findViewById(R.id.registerBut);
        descriptionET = findViewById(R.id.descriptionET);
        jobSpinner = findViewById(R.id.jobSpinner);
        controller = new RegisterController(session,this);

        sessionImage = findViewById(R.id.sessionImage);
        iAmTV = findViewById(R.id.iAmTV);
        addFilesTV = findViewById(R.id.addFilesTV);

        if(session.equals(Constants.SESSION_CLIENT)) {
            sessionImage.setImageResource(R.drawable.client);
            iAmTV.setText(getString(R.string.register_client_title));
            addFilesTV.setText(R.string.addFilesClient);
        }
        else {
            sessionImage.setImageResource(R.drawable.worker);
            iAmTV.setText(getString(R.string.register_expert_title));
            addFilesTV.setText(R.string.addFilesExpert);
        }
    }

    public ImageView getAddPhotoIV() {
        return addPhotoIV;
    }

    public EditText getPasswordET() {
        return passwordET.getEditText();
    }

    public EditText getDocumentET() {
        return documentET.getEditText();
    }
    public EditText getFistNameET() {
        return fistNameET.getEditText();
    }
    public EditText getLastNameET() {
        return lastNameET.getEditText();
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
