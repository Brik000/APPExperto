package com.example.appexperto2020.control;

import android.content.Intent;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.view.ChooseTypeOfRegister;
import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.MainActivity;

public class MainController implements View.OnClickListener{

    private MainActivity view;

    public MainController(MainActivity main) {
        view = main;
        view.getButRegisterMain().setOnClickListener(this);
        view.getButLoginClient().setOnClickListener(this);
        view.getButLoginWorker().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butLoginClient:
                Intent i = new Intent(view, LoginActivity.class);
                i.putExtra(Constants.LOG_IN_TYPE,Constants.LOG_IN_CLIENT);
                view.startActivity(i);
                Animatoo.animateFade(view);
                break;
            case R.id.butLoginWorker:
                Intent in = new Intent(view, LoginActivity.class);
                in.putExtra(Constants.LOG_IN_TYPE,Constants.LOG_IN_EXPERT);
                view.startActivity(in);
                Animatoo.animateFade(view);
                break;
            case R.id.butRegisterMain:
                Intent i2 = new Intent(view, ChooseTypeOfRegister.class);
                view.startActivity(i2);
                Animatoo.animateFade(view);
                break;
        }
    }
}
