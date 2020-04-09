package com.example.appexperto2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

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

                        }else if(offerServiceSwitch.isChecked()){

                        }
                    }
                }
        );
    }
}
