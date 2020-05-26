package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

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
import static com.example.appexperto2020.util.Constants.SESSION_EXPERT;

public class UserMainController implements View.OnClickListener{

    private UserMainFragment activity;

    private HashMap<String, String> interests;
    private String user;
    String session;
    private String folder;

    public UserMainController(UserMainFragment activity, String session)
    {
        this.activity = activity;
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
        getInterests();
        findExpertsByInterests();

    }

    public void checkJob(Expert expert)
    {
        Object[] keys  = interests.keySet().toArray();
        HashMap<String,Job> jobs = expert.getJobList();
        ArrayList<Expert> experts = new ArrayList<>();
        for(int i = 0; i<keys.length;i++)
        {
            if(jobs.containsKey(keys[i]))
            {
                Log.e(">>>>>>>>", "INTEREST"+expert.getFirstName());
                experts.add(expert);

                activity.getAdapter().addExpert(expert);
                activity.getAdapter().notifyDataSetChanged();

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
            case R.id.goToBtn:
                String id = v.getContentDescription().toString();
                activity.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSelected, new UserProfileFragment(id)).commit();
            break;
        }
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

}

