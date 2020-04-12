package com.example.appexperto2020.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.view.ChooseTypeOfRegisterActivity;
import com.example.appexperto2020.view.ExpertRegistrationActivity;
import com.example.appexperto2020.view.CreateUserActivity;

public class ChooseTypeRegistrationController implements View.OnClickListener {

    private ChooseTypeOfRegisterActivity view;

    public ChooseTypeRegistrationController(ChooseTypeOfRegisterActivity view)
    {
        this.view = view;
        this.view.getRegisterBtn().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            AlertDialog alertDialog = new AlertDialog.Builder(this.view).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Escoge solo una opción");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            if (this.view.getOfferServiceSwitch().isChecked() && this.view.getSearchServiceSwitch().isChecked()) {
                alertDialog.show();
            } else if (!this.view.getOfferServiceSwitch().isChecked() && !this.view.getSearchServiceSwitch().isChecked()) {
                alertDialog.setMessage("Escoge una opción para poder registrarte");
                alertDialog.show();
            } else {
                if (this.view.getOfferServiceSwitch().isChecked()) {

                    Intent i = new Intent(this.view, ExpertRegistrationActivity.class);
                    view.startActivity(i);
                    Animatoo.animateFade(view);

                } else if (this.view.getSearchServiceSwitch().isChecked()) {
                    Intent i2 = new Intent(this.view, CreateUserActivity.class);
                    view.startActivity(i2);
                    Animatoo.animateFade(view);

                }
            }


    }
}
