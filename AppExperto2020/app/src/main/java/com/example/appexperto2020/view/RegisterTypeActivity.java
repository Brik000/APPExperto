package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.RegisterTypeController;

import lombok.Getter;

public class RegisterTypeActivity extends AppCompatActivity {
    @Getter
    private Button registerBtn;
    @Getter
    private Switch offerServiceSwitch,searchServiceSwitch;
    @Getter
    private TextView regTypeTitleTV;

    private RegisterTypeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);

        registerBtn = findViewById(R.id.registerBtn);
        offerServiceSwitch = findViewById(R.id.offerServiceSwitch);
        searchServiceSwitch = findViewById(R.id.searchServiceSwitch);
        regTypeTitleTV = findViewById(R.id.regTypeTitleTV);

        controller = new RegisterTypeController(this);

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Animatoo.animateFade(this);
    }
}
