package com.example.appexperto2020.control;

import android.content.Intent;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.UsersMainActivity;

public class LogInController implements View.OnClickListener {

    public LoginActivity activity;

    public LogInController(LoginActivity activity)
    {
        this.activity = activity;
        this.activity.getButLogin().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.butLoginClient:
                Intent i = new Intent(activity, UsersMainActivity.class);
                i.putExtra("userName", "Maria Camila");
                activity.startActivity(i);
                break;
            case R.id.butLoginWorker:
                Intent i2 = new Intent(activity, UsersMainActivity.class);
                i2.putExtra("userName", "Maria Camila");
                activity.startActivity(i2);
                break;
            case R.id.butLogin:
                Intent i3 = new Intent(activity, UsersMainActivity.class);
                i3.putExtra("userName", "Maria Camila");
                activity.startActivity(i3);
                break;
        }
    }
}
