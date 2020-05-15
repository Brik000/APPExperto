package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.LogInController;
import com.example.appexperto2020.util.Constants;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextView txtLogining;
    private TextInputLayout editUserLog;
    private TextInputLayout editPasswordLog;
    private Button butLogin;
    private LogInController controller;

    private String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = getIntent().getExtras().getString(Constants.SESSION_TYPE);
        txtLogining = findViewById(R.id.txtLogining);
        editUserLog = findViewById(R.id.mailLoginET);
        editPasswordLog = findViewById(R.id.passwordLoginET);
        butLogin = findViewById(R.id.butLogin);
        txtLogining.setText(session);
        controller = new LogInController(this);
    }

    public TextView getTxtLogining() {
        return txtLogining;
    }

    public EditText getEditUserLog() {
        return editUserLog.getEditText();
    }

    public EditText getEditPasswordLog() {
        return editPasswordLog.getEditText();
    }

    public Button getButLogin() {
        return butLogin;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }

    private String getSession()
    {
        return session;
    }
}
