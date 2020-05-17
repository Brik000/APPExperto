package com.example.appexperto2020.control;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.appexperto2020.ExpertDetails;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpertDetailsController {
    private ExpertDetails activity;

    public ExpertDetailsController(ExpertDetails activity) {
        this.activity = activity;
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("experts")
                .child(activity.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Expert expert = dataSnapshot.getValue(Expert.class);

                ArrayList<String> data = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,android.R.layout.simple_list_item_1,data);
                for(Job j: expert.getJobList().values()){
                    data.add(j.getName());
                    adapter.notifyDataSetChanged();
                }
                activity.runOnUiThread(
                        ()->{
                            activity.getExpertDetailsCellphoneTxt().setText(expert.getCellphone()+"");
                            activity.getExpertDetailsDescriptionTxt().setText(expert.getDescription());
                            activity.getExpertDetailsLastNameTxt().setText(expert.getLastName());
                            activity.getExpertDetailsNameTxt().setText(expert.getFirstName());
                            activity.getExpertDetailJobsList().setAdapter(adapter);
                        }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
