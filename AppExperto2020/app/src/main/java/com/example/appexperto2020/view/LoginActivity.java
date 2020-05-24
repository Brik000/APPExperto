package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.LogInController;
import com.google.android.material.textfield.TextInputLayout;

import lombok.Getter;

import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class LoginActivity extends AppCompatActivity {

    @Getter
    private TextView txtLogining;
    @Getter
    private TextInputLayout editUserLog;
    @Getter
    private TextInputLayout editPasswordLog;
    @Getter
    private Button butLogin;
    @Getter
    private ProgressBar progressBar;
    @Getter
    private String session;

    private LogInController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = getIntent().getExtras().getString(SESSION_TYPE);
        txtLogining = findViewById(R.id.txtLogining);
        editUserLog = findViewById(R.id.mailLoginET);
        editPasswordLog = findViewById(R.id.passwordLoginET);
        butLogin = findViewById(R.id.butLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        txtLogining.setText(session);
        controller = new LogInController(this);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Animatoo.animateFade(this);
    }
}
