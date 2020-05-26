package com.example.appexperto2020.control;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.UserMainFragment;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class LogInController implements View.OnClickListener {

    private LoginActivity activity;
    private String session;

    public LogInController(LoginActivity activity)
    {
        this.activity = activity;
       session = this.activity.getIntent().getExtras().getString(SESSION_TYPE);
        this.activity.getButLogin().setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.butLogin:
                if(activity.getEditPasswordLog().getEditText().getText().toString().trim().isEmpty()
                        || activity.getEditUserLog().getEditText().getText().toString().trim().isEmpty()){
                    Toast.makeText(activity, activity.getString(R.string.fill_blank_spaces),Toast.LENGTH_LONG).show();
                    return;
                }
                activity.getButLogin().setEnabled(false);
                activity.getProgressBar().setVisibility(View.VISIBLE);
                activity.getButLogin().setEnabled(false);
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        activity.getEditUserLog().getEditText().getText().toString(),
                        activity.getEditPasswordLog().getEditText().getText().toString()).addOnSuccessListener(
                                authResult -> {
                                    loadingFinished();
                                    Intent i = new Intent(activity, UserMainFragment.class);
                                    i.putExtra(SESSION_TYPE, session);
                                    activity.startActivity(i);
                                    Animatoo.animateSwipeLeft(activity);
                }).addOnFailureListener(e -> {
                    loadingFinished();
                    Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
                break;
        }
    }

    private void loadingFinished() {
        activity.getButLogin().setEnabled(true);
        activity.getProgressBar().setVisibility(View.GONE);
    }
}
