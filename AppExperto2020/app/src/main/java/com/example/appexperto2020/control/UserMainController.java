package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.view.UserProfileFragment;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.view.UserMainFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.appexperto2020.util.Constants.FOLDER_CLIENTS;
import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;
import static com.example.appexperto2020.util.Constants.SESSION_CLIENT;
import static com.example.appexperto2020.util.Constants.SESSION_EXPERT;

public class UserMainController implements View.OnClickListener{

    private UserMainFragment activity;

    private HashMap<String, String> interests;
    private String user;
    String session;
    private String folder;

    ArrayList<Expert> experts;

    public UserMainController(UserMainFragment activity, String session)
    {
        this.activity = activity;
        experts = new ArrayList<>();

        this.session = session;
        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.HORIZONTAL));

        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.VERTICAL));


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(session.equals(SESSION_EXPERT))
            folder = FOLDER_EXPERTS;
        else folder = FOLDER_CLIENTS;

        FirebaseDatabase.getInstance().getReference().child(folder).child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

        if(session.equals(SESSION_CLIENT)){
            getInterests(uid);
            findExpertsByInterests();
        }



    }

    public void checkJob(Expert expert)
    {
        Object[] keys  = interests.keySet().toArray();
        HashMap<String,Job> jobs = expert.getJobList();
        ArrayList<Expert> experts = new ArrayList<>();
        for(int i = 0; i<keys.length;i++)
        {
            if(jobs != null && jobs.containsKey(keys[i]))
            {
                experts.add(expert);
                activity.getAdapter().addExpert(expert);
                activity.getAdapter().notifyDataSetChanged();

            }

            break;
        }
    }

    public void getInterests(String uid) {
        interests = new HashMap<>();

        FirebaseDatabase.getInstance().getReference().child("clients").child(uid).child("interests")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            Job job = d.getValue(Job.class);
                            interests.put(job.getId(), job.getId());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToBtn:
                String id = v.getContentDescription().toString();
                FragmentManager fm = activity.getActivity().getSupportFragmentManager();
                UserProfileFragment userProfileFragment = new UserProfileFragment(id);
                fm.beginTransaction().add(R.id.fragmentSelected, userProfileFragment, "temporal").commit();
                fm.beginTransaction().hide(fm.getPrimaryNavigationFragment()).commit();
                fm.beginTransaction().setPrimaryNavigationFragment(userProfileFragment).commit();
                fm.beginTransaction().show(userProfileFragment).commit();
            break;
        }
    }

    public void findExpertsByInterests() {

        ArrayList<Expert> expertsFromServer = new ArrayList<>();
        Query q = FirebaseDatabase.getInstance().getReference().child(FOLDER_EXPERTS);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    checkJob(expert);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">>>>", databaseError.toString());

            }
        });
    }

}

