package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appexperto2020.adapter.ExpertAdapter;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.view.UsersMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class UserMainController implements View.OnClickListener{

    private UsersMainActivity activity;
    private ExpertAdapter expertAdapter;
    private HashMap<String, String> interests;
    public ArrayList<Expert> aExperts;
    public ArrayList<Expert> experts;
    public ArrayList<Job> interestJobs;

    public UserMainController(UsersMainActivity activity)
    {
        this.activity = activity;
        String username = (String) activity.getIntent().getExtras().get("userName");
        Log.e(">>>>",username);
        String[] firtName = username.split(" ");
        aExperts= new ArrayList<>();
        activity.getWelcomeTV().setText("Bienvenid@ " +firtName[0]);
        interests = new HashMap<String, String>();
        interests.put("-M7Ik4dVFjoJuCPGXj3o", "-M7Ik4dVFjoJuCPGXj3o");
        interests.put("-M7Ik4eVqhZB1R5B7kaw","-M7Ik4eVqhZB1R5B7kaw");

        findExpertsByInterests();

        for(int i = 0; i < interests.size(); i++)
        {
            for(int j=0; j<aExperts.size();j++)
            {
                for(int k = 0; k<aExperts.get(j).getJobList().size(); k++)
                {
                    if(interests.containsKey(aExperts.get(j).getJobList().get(k)))
                    {
                        experts.add(aExperts.get(j));
                    }
                }

            }
        }




        LinearLayoutManager linearLayoutManager = new GridLayoutManager(activity, 2);
      //  linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activity.getExpertsRV().setLayoutManager(linearLayoutManager);
        expertAdapter = new ExpertAdapter(experts, this);
        activity.getExpertsRV().setAdapter(expertAdapter);


    }

    @Override
    public void onClick(View v) {

    }

    public void findExpertsByInterests(){
        Query q = FirebaseDatabase.getInstance().getReference().child("experts");
        experts = new ArrayList<>();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aExperts = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    Log.e(">>>>>>>",expert.getFirstName());

                    aExperts.add(expert);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}

