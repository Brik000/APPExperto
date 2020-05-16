package com.example.appexperto2020.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.view.RegisterTypeActivity;
import com.example.appexperto2020.view.RegisterActivity;

public class RegisterTypeController implements View.OnClickListener, Switch.OnCheckedChangeListener {

    private RegisterTypeActivity view;

    public RegisterTypeController(RegisterTypeActivity view)
    {
        this.view = view;
        this.view.getRegisterBtn().setOnClickListener(this);
        view.getOfferServiceSwitch().setOnCheckedChangeListener(this);
        view.getSearchServiceSwitch().setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        AlertDialog alertDialog = new AlertDialog.Builder(view).create();
        alertDialog.setTitle(view.getString(R.string.alert_title));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Vale",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

            if (!view.getOfferServiceSwitch().isChecked() && !view.getSearchServiceSwitch().isChecked()) {
                alertDialog.setMessage(view.getString(R.string.choose_one));
                alertDialog.show();
            } else {
                Intent i = new Intent(this.view, RegisterActivity.class);

                if (view.getOfferServiceSwitch().isChecked()) {
                    i.putExtra(Constants.SESSION_TYPE,Constants.SESSION_EXPERT);
                } else if (view.getSearchServiceSwitch().isChecked()) {
                    i.putExtra(Constants.SESSION_TYPE, Constants.SESSION_CLIENT);
                }
                view.startActivity(i);
                    Animatoo.animateFade(view);

            }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Switch other;
        switch (buttonView.getId()) {
            case R.id.offerServiceSwitch:
                other = view.getSearchServiceSwitch();
                if(isChecked)
                    other.setChecked(false);
                else other.setChecked(true);
                break;
            case R.id.searchServiceSwitch:
                other = view.getOfferServiceSwitch();
                if(isChecked)
                    other.setChecked(false);
                else other.setChecked(true);
                break;
        }
    }
}
