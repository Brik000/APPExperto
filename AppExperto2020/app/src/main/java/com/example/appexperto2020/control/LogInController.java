package com.example.appexperto2020.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.NavBarActivity;
import com.example.appexperto2020.view.UserMainFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;
import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class LogInController implements View.OnClickListener {

    private LoginActivity activity;
    private String session;

    public LogInController(LoginActivity activity)
    {
        this.activity = activity;
       session = this.activity.getIntent().getExtras().getString(SESSION_TYPE);
        Log.e("session", session);
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
                                    insuranceUserType();
                }).addOnFailureListener(e -> {
                    loadingFinished();
                    Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
                break;
        }
    }

    private void insuranceUserType() {
        String folder;
        if(session.equals(Constants.SESSION_CLIENT))
            folder = Constants.FOLDER_CLIENTS;
        else folder = FOLDER_EXPERTS;
        FirebaseDatabase.getInstance().getReference().child(folder).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        loadingFinished();
                        if(dataSnapshot.getValue() != null) {
                            Intent i = new Intent(activity, NavBarActivity.class);
                            i.putExtra(SESSION_TYPE, session);
                            activity.startActivity(i);
                            Animatoo.animateSwipeLeft(activity);
                        } else {
                            Toast.makeText(activity, activity.getString(R.string.usertype_incorrect), Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
                );
    }

    private void loadingFinished() {
        activity.getButLogin().setEnabled(true);
        activity.getProgressBar().setVisibility(View.GONE);
    }
}
