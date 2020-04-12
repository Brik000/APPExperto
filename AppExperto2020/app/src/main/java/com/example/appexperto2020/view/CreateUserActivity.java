package com.example.appexperto2020.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;

import java.util.ArrayList;
import java.util.List;

public class CreateUserActivity extends AppCompatActivity {

    private EditText txtUserName;

    private EditText txtEmailUser;

    private EditText txtPassword;

    private EditText txtRepeatPassword;

    private EditText txtDescription;

    private Button btnNewUser;

    Spinner spinnerIntereses;






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
        spinnerIntereses=(Spinner)findViewById(R.id.inputIterests);

        //TODO
        //get interests from database
        List<String> list = new ArrayList<String>();
        list.add("List1");
        list.add("List2");
        spinnerIntereses.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }

}
