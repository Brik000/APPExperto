package com.example.appexperto2020.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.appexperto2020.R;
import com.example.appexperto2020.view.RequestServiceActivity;
import com.example.appexperto2020.view.UserProfileFragment;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfileController implements View.OnClickListener{
    private UserProfileFragment activity;
    private Expert expert;
    public UserProfileController(UserProfileFragment activity) {
        this.activity = activity;
        activity.getServiceButton().setOnClickListener(this);
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("experts")
                .child(activity.getUId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expert = dataSnapshot.getValue(Expert.class);

                ArrayList<String> data = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getActivity(),android.R.layout.simple_list_item_1,data);
                if(expert.getJobList() != null)
                for(Job j: expert.getJobList().values()){
                    data.add(j.getName());
                    adapter.notifyDataSetChanged();
                }
                activity.getActivity().runOnUiThread(
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.serviceButton:
                Intent i = new Intent(v.getContext(), RequestServiceActivity.class);
                Log.e("idE", expert.getId());
                i.putExtra("idE", expert.getId());
                v.getContext().startActivity(i);
                break;
        }
    }
}
