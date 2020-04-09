package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;

public class LoginActivity extends AppCompatActivity {

    private TextView txtLogining;
    private EditText editUserLog;
    private EditText editPasswordLog;
    private Button butLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String llegada = getIntent().getExtras().getString(Constants.LOG_IN_TYPE);
        txtLogining = findViewById(R.id.txtLogining);
        editUserLog = findViewById(R.id.editUserLog);
        editPasswordLog = findViewById(R.id.editPasswordLog);
        butLogin = findViewById(R.id.butRegistro);
        txtLogining.setText(llegada);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }
}
