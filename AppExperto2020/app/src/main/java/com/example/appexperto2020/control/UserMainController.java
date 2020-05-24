package com.example.appexperto2020.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appexperto2020.ExpertDetails;
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
import java.util.Set;

public class UserMainController implements View.OnClickListener{

    private UsersMainActivity activity;
    private ExpertAdapter expertAdapter;

    private ArrayList<Expert> experts;
    private HashMap<String, String> interests;
    public UserMainController(UsersMainActivity activity)
    {

        this.experts = new ArrayList<>();
        this.expertAdapter = new ExpertAdapter();
        this.activity = activity;
        activity.getExpertsRV().setAdapter(expertAdapter);


        String username = (String) activity.getIntent().getExtras().get("userName");
        activity.getExpertsRV().setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activity.getExpertsRV().setLayoutManager(linearLayoutManager);

        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.VERTICAL));

        String[] firstName = username.split(" ");

        activity.getWelcomeTV().setText("Bienvenid@ " +firstName[0]);
        getInterests();
        findExpertsByInterests();

    }


    @Override
    public void onClick(View v) {

        Intent i = new Intent(v.getContext(), ExpertDetails.class);
        i.putExtra("id", v.getContentDescription().toString());
        v.getContext().startActivity(i);
    }

    public void getInterests()
    {
        interests = new HashMap<>();
        interests.put("-M7IlMXw4PfA1HKejHC3","-M7IlMXw4PfA1HKejHC3");
        interests.put( "-M7IlMXzdZlx9zcnGJ1i", "-M7IlMXzdZlx9zcnGJ1i");
    }

    public void findExpertsByInterests() {

        ArrayList<Expert> expertsFromServer = new ArrayList<>();
        Query q = FirebaseDatabase.getInstance().getReference().child("experts");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    Log.e(">>>>>>>",expert.getFirstName());
                    checkJob(expert);
                    expertsFromServer.add(expert);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">>>>", databaseError.toString());

            }
        });
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


                break;
            }
        }

    }
}

