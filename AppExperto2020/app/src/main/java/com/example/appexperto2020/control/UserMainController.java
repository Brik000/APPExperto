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

    public UserMainController(UsersMainActivity activity)
    {
        this.activity = activity;
        String username = (String) activity.getIntent().getExtras().get("userName");
        String[] firtName = username.split(" ");
        activity.getWelcomeTV().setText("Bienvenid@ " +firtName[0]);
        findExpertsByInterests();
    }

    @Override
    public void onClick(View v) {

    }

    public void findExpertsByInterests(){
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
                }
                activity.getAdapter().setData(experts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}

