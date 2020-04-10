package com.example.appexperto2020.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;

public class ChooseTypeOfRegister extends AppCompatActivity {

    private Button registerBtn;
    private Switch offerServiceSwitch,searchServiceSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type_of_register);

        registerBtn = findViewById(R.id.registerBtn);
        offerServiceSwitch = findViewById(R.id.offerServiceSwitch);
        searchServiceSwitch = findViewById(R.id.searchServiceSwitch);

        registerBtn.setOnClickListener(

                (view)->{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Escoge solo una opción");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    if(offerServiceSwitch.isChecked() && searchServiceSwitch.isChecked()){
                        alertDialog.show();
                    }else{
                        if(offerServiceSwitch.isChecked()){

                        }else if(searchServiceSwitch.isChecked()){
                            Intent in = new Intent(this, CreateUserActivity.class);
                            startActivity(in);
                            Animatoo.animateFade(this);
                        }
                    }
                }
        );
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }
}
