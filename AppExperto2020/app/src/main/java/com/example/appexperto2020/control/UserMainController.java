package com.example.appexperto2020.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.adapter.ExpertAdapter;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.UserProfileActivity;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.view.LoginActivity;
import com.example.appexperto2020.view.UserMainActivity;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.example.appexperto2020.util.Constants.FOLDER_CLIENTS;
import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;
import static com.example.appexperto2020.util.Constants.SESSION_EXPERT;
import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class UserMainController implements View.OnClickListener{

    private UserMainActivity activity;
    private ExpertAdapter expertAdapter;

    private ArrayList<Expert> experts;
    private HashMap<String, String> interests;
    private String user;
    String session;
    private String folder;

    public UserMainController(UserMainActivity activity)
    {
     

        this.activity = activity;
        activity.getExpertsRV().setAdapter(expertAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activity.getExpertsRV().setLayoutManager(linearLayoutManager);

        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.HORIZONTAL));

        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.VERTICAL));


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
        getInterests();
        findExpertsByInterests();

    }

    public void checkJob(Expert expert)
    {
        Object[] keys  = interests.keySet().toArray();
        HashMap<String,Job> jobs = expert.getJobList();
        for(int i = 0; i<keys.length;i++)
        {
            if(jobs.containsKey(keys[i]))
            {
                Log.e(">>>>>>>>", "INTEREST"+expert.getFirstName());
                experts.add(expert);

                Log.e(">>>>>>>>", expertAdapter.toString());


                expertAdapter.addExpert(expert);
                expertAdapter.notifyDataSetChanged();

            }

            break;
        }
    }

    public void getInterests()
    {
        interests = new HashMap<>();
        interests.put("-M7IlMXw4PfA1HKejHC3","-M7IlMXw4PfA1HKejHC3");
        interests.put( "-M7IlMXzdZlx9zcnGJ1i", "-M7IlMXzdZlx9zcnGJ1i");
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

        if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired())
            LoginManager.getInstance().logOut();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseAuth.getInstance().signOut();
        Intent i1 = new Intent(activity, LoginActivity.class);
        i1.putExtra(SESSION_TYPE, session);
        activity.startActivity(i1);
        Animatoo.animateDiagonal(activity);
    }

    public void findExpertsByInterests() {

        ArrayList<Expert> expertsFromServer = new ArrayList<>();
        Query q = FirebaseDatabase.getInstance().getReference().child("experts");
        HashMap<String, String> interests = new HashMap<String, String>();
        interests.put("-M7Ik4dVFjoJuCPGXj3o", "-M7Ik4dVFjoJuCPGXj3o");
        interests.put("-M7Ik4eVqhZB1R5B7kaw","-M7Ik4eVqhZB1R5B7kaw");
        ArrayList experts = new ArrayList<>();

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    Log.e(">>>>>>>",expert.getFirstName());
                    experts.add(expert);
                    checkJob(expert);
                    expertsFromServer.add(expert);
                }
                activity.getAdapter().setData(experts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">>>>", databaseError.toString());

            }
        });
    }
    public void logOutDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                   //     signOut();
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

