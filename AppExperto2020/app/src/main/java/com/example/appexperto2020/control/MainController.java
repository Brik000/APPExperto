package com.example.appexperto2020.control;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.view.RegisterTypeActivity;

import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.MainActivity;

public class MainController implements View.OnClickListener{

    private MainActivity view;

    public MainController(MainActivity view) {


        this.view = view;

        ActivityCompat.requestPermissions(view, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        },0);
        view.getButRegisterMain().setOnClickListener(this);
        view.getButLoginClient().setOnClickListener(this);
        view.getButLoginWorker().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butLoginClient:
                Intent i = new Intent(view, LoginActivity.class);
                i.putExtra(Constants.SESSION_TYPE,Constants.SESSION_CLIENT);
                view.startActivity(i);
                Animatoo.animateFade(view);
                break;
            case R.id.butLoginWorker:
                Intent in = new Intent(view, LoginActivity.class);
                in.putExtra(Constants.SESSION_TYPE,Constants.SESSION_EXPERT);
                view.startActivity(in);
                Animatoo.animateFade(view);
                break;
            case R.id.butRegisterMain:
                Intent reg = new Intent(view, RegisterTypeActivity.class);
                view.startActivity(reg);
                Animatoo.animateFade(view);


                break;
        }
    }
}
