package com.example.appexperto2020.control;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;

import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.MainActivity;
import com.example.appexperto2020.view.NavBarActivity;
import com.example.appexperto2020.view.RegisterTypeActivity;

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

    public void goToUserMain(String session) {
        Intent i = new Intent(view, NavBarActivity.class);
        //Hacer una búsqueda para saber si se encuentra en expertos o en clientes
        i.putExtra(Constants.SESSION_TYPE, session);
        view.startActivity(i);
        Animatoo.animateCard(view);
    }

    public void goToRegisterAfterFacebook(String firstName, String lastName, String urlPP) {
        Intent reg = new Intent(view, RegisterTypeActivity.class);
        reg.putExtra(Constants.FACEBOOK_FIRST_NAME, firstName);
        reg.putExtra(Constants.FACEBOOK_LAST_NAME, lastName);
        if(urlPP != null)
        reg.putExtra(Constants.FACEBOOK_PP_URL, urlPP);
        view.startActivity(reg);
        Animatoo.animateFade(view);
    }
}
