package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ChooseTypeRegistrationController;

public class ChooseTypeOfRegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private Switch offerServiceSwitch,searchServiceSwitch;
    private ChooseTypeRegistrationController controller;

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
        setContentView(R.layout.activity_choose_type_of_registration);

        registerBtn = findViewById(R.id.registerBtn);
        offerServiceSwitch = findViewById(R.id.offerServiceSwitch);
        searchServiceSwitch = findViewById(R.id.searchServiceSwitch);

        controller = new ChooseTypeRegistrationController(this);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }
}
