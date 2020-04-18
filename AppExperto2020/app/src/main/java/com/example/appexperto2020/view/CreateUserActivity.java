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
import com.example.appexperto2020.control.UserRegistrationController;
import com.example.appexperto2020.util.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.List;

public class CreateUserActivity extends AppCompatActivity {

    private UserRegistrationController controller;

    private EditText txtUserName;

    private EditText txtEmailUser;

    private EditText txtPassword;

    private EditText txtRepeatPassword;

    private EditText txtDescription;

    private EditText txtNombreApellido;

    private Button btnNewUser;

    Spinner spinnerIntereses;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_create_user);
        txtNombreApellido=findViewById(R.id.editTextNombre);
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
        ((MultiSelectionSpinner)spinnerIntereses).setItems(list);
        controller=new UserRegistrationController(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }

    public EditText getTxtNombreApellido() {
        return txtNombreApellido;
    }

    public void setTxtNombreApellido(EditText txtNombreApellido) {
        this.txtNombreApellido = txtNombreApellido;
    }

    public EditText getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(EditText txtUserName) {
        this.txtUserName = txtUserName;
    }

    public EditText getTxtEmailUser() {
        return txtEmailUser;
    }

    public void setTxtEmailUser(EditText txtEmailUser) {
        this.txtEmailUser = txtEmailUser;
    }

    public EditText getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(EditText txtPassword) {
        this.txtPassword = txtPassword;
    }

    public EditText getTxtRepeatPassword() {
        return txtRepeatPassword;
    }

    public void setTxtRepeatPassword(EditText txtRepeatPassword) {
        this.txtRepeatPassword = txtRepeatPassword;
    }

    public EditText getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(EditText txtDescription) {
        this.txtDescription = txtDescription;
    }

    public Button getBtnNewUser() {
        return btnNewUser;
    }

    public void setBtnNewUser(Button btnNewUser) {
        this.btnNewUser = btnNewUser;
    }

    public Spinner getSpinnerIntereses() {
        return spinnerIntereses;
    }

    public void setSpinnerIntereses(Spinner spinnerIntereses) {
        this.spinnerIntereses = spinnerIntereses;
    }
}
