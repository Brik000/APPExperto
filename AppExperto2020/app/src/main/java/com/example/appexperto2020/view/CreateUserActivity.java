package com.example.appexperto2020.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.MultiSelectionSpinner;

public class CreateUserActivity extends AppCompatActivity {

    private EditText txtUserName;

    private EditText txtEmailUser;

    private EditText txtPassword;

    private EditText txtRepeatPassword;

    private EditText txtDescription;

    private Button btnNewUser;

    private MultiSelectionSpinner spinnerIntereses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        txtUserName=findViewById(R.id.editTextUserSingIn);
        txtEmailUser=findViewById(R.id.editTextMail);
        txtPassword=findViewById(R.id.editPasswordLog);
        txtRepeatPassword=findViewById(R.id.editTextRepeatPassword);
        txtDescription=findViewById(R.id.editTextDescriptionNewUser);
        btnNewUser=findViewById(R.id.butNewUser);
        spinnerIntereses=findViewById(R.id.inputIterests);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }

}
