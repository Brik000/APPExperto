package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.RegisterTypeController;

public class RegisterTypeActivity extends AppCompatActivity {

    private Button registerBtn;
    private Switch offerServiceSwitch,searchServiceSwitch;
    private RegisterTypeController controller;

    public Button getRegisterBtn() {
        return registerBtn;
    }

    public Switch getOfferServiceSwitch() {
        return offerServiceSwitch;
    }

    public Switch getSearchServiceSwitch() {
        return searchServiceSwitch;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);

        registerBtn = findViewById(R.id.registerBtn);
        offerServiceSwitch = findViewById(R.id.offerServiceSwitch);
        searchServiceSwitch = findViewById(R.id.searchServiceSwitch);

        controller = new RegisterTypeController(this);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }
}
