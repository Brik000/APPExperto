package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.UserProfileController;

import lombok.Data;
import lombok.Getter;

public class UserProfileActivity extends AppCompatActivity {
    @Getter
    private TextView expertDetailsNameTxt,expertDetailsLastNameTxt,expertDetailsDescriptionTxt,expertDetailsCellphoneTxt;
    @Getter
    private ListView expertDetailJobsList;
    @Getter
    private Button serviceButton;
    @Getter
    private String id;
    private UserProfileController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        serviceButton = findViewById(R.id.serviceButton);
        expertDetailsNameTxt= findViewById(R.id.expertDetailsNameTxt);
        expertDetailsLastNameTxt= findViewById(R.id.expertDetailsLastNameTxt);
        expertDetailsDescriptionTxt = findViewById(R.id.expertDetailsDescriptionTxt);
        expertDetailsCellphoneTxt = findViewById(R.id.expertDetailsCellphoneTxt);

        expertDetailJobsList = findViewById(R.id.expertDetailJobsList);
        id = getIntent().getExtras().getString("id");
        controller = new UserProfileController(this);
    }
}
