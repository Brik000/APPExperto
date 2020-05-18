package com.example.appexperto2020.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.view.UserProfileActivity;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.UserMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.appexperto2020.util.Constants.FOLDER_CLIENTS;
import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;
import static com.example.appexperto2020.util.Constants.SESSION_EXPERT;
import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class UserMainController implements View.OnClickListener{

    private UserMainActivity activity;
    private String user;
    String session;
    private String folder;

    public UserMainController(UserMainActivity activity)
    {
        this.activity = activity;
        String uid = FirebaseAuth.getInstance().getUid();
        session = activity.getIntent().getExtras().getString(SESSION_TYPE);

        if(session.equals(SESSION_EXPERT))
            folder = FOLDER_EXPERTS;
        else folder = FOLDER_CLIENTS;
        FirebaseDatabase.getInstance().getReference().child(folder).child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e(">>>","LLega antes del rechazo");
                        if(folder.equals(FOLDER_EXPERTS))
                        user = dataSnapshot.child("firstName").getValue(String.class);
                        else user = dataSnapshot.child("firstName").getValue(String.class);
                        activity.getWelcomeTV().setText(activity.getString(R.string.welcome_word)+" " +user);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                }
    }
        );
        activity.getLogOutIV().setOnClickListener(this);
        findExpertsByInterests();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logOutIV:
                logOutDialog();
                break;
            case R.id.goToBtn:
            Intent i = new Intent(v.getContext(), UserProfileActivity.class);
            i.putExtra("id", v.getContentDescription().toString());
            v.getContext().startActivity(i);
            break;
        }
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i1 = new Intent(activity, LoginActivity.class);
        i1.putExtra(SESSION_TYPE, session);
        activity.startActivity(i1);
        Animatoo.animateDiagonal(activity);
    }

    public void findExpertsByInterests(){
        activity.getProgressBar().setVisibility(View.VISIBLE);
        Query q  = FirebaseDatabase.getInstance().getReference().child("experts");
        HashMap<String, String> interests = new HashMap<String, String>();
        interests.put("-M7Ik4dVFjoJuCPGXj3o", "-M7Ik4dVFjoJuCPGXj3o");
        interests.put("-M7Ik4eVqhZB1R5B7kaw","-M7Ik4eVqhZB1R5B7kaw");
        ArrayList experts = new ArrayList<>();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()){
                    Expert expert = user.getValue(Expert.class);
//                    Log.e(">>","va a obtener el firstName");
//                    String attribute = user.child("firstName").getValue(String.class);
//                    expert.setFirstName(attribute);
//                    Log.e(">>","va a obtener el lastName");
//                    attribute = user.child("lastName").getValue(String.class);
//                    expert.setLastName(attribute);
//                    Log.e(">>","va a obtener el jobList");
//                    if(user.child("jobList").getKey() != null) {
//                        Type t = new TypeToken<HashMap<String, Job>>() {
//                        }.getType();
//                        expert.setJobList(user.child("jobList").getValue(t));
//                    }
//                    Log.e(">>>>>>>", expert.getFirstName());
                    experts.add(expert);
                }
                activity.getProgressBar().setVisibility(View.GONE);
                activity.getAdapter().setData(experts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void logOutDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        signOut();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.sign_out_ask_message)).setPositiveButton(activity.getString(R.string.agree), dialogClickListener)
                .setNegativeButton(R.string.no_answer, dialogClickListener).show();
    }
}

