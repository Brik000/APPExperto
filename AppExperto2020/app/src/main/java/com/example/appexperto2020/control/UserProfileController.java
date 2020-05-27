package com.example.appexperto2020.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.view.RequestServiceActivity;
import com.example.appexperto2020.view.UserProfileFragment;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import lombok.Getter;

import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;
import static com.example.appexperto2020.util.Constants.FOLDER_PROFILE_PICTURES;

public class UserProfileController implements View.OnClickListener{
   @Getter
    private UserProfileFragment activity;
    private Expert expert;
    private String jobs;

    public UserProfileController(UserProfileFragment activity) {
        this.activity = activity;
        activity.getServiceText().setOnClickListener(this);
        activity.getServiceBtn().setOnClickListener(this);
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("experts")
                .child(activity.getUId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expert = dataSnapshot.getValue(Expert.class);

                jobs = "";
                for(Job j: expert.getJobList().values()){
                    if(jobs.equals(""))
                    {
                        jobs += j.getName();
                    }else {
                        jobs += " || " + j.getName();
                    }
                }

                setProfilePicture();
                setJobPhotos();
                activity.getActivity().runOnUiThread(
                        ()->{
                            activity.getNameTV().setText(expert.getFirstName() + " " + expert.getLastName());
                            activity.getDescriptionTV().setText(expert.getDescription());
                            activity.getJobTV().setText(jobs);
                            activity.getMailTV().setText(expert.getEmail());
                            activity.getPhoneTV().setText(expert.getCellphone()+"");



                        }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setJobPhotos()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        ArrayList<String> uris = new ArrayList<>();
        StorageReference listRef = storage.getReference().child(FOLDER_EXPERTS).child(expert.getId());
        listRef.listAll()
                .addOnSuccessListener(listResult -> {

                    for (StorageReference item : listResult.getItems()) {
                           String[] ref = item.toString().split("/");
                           String uri = ref[ref.length-1];
                           uris.add(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(">>>>>", "ERROR BRINGING ITEMS");
                    }
                });
        activity.getAdapter().setIdExpert(expert.getId());
        activity.getAdapter().setData(uris);
    }
    public void setProfilePicture()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        try{
            storage.getReference().child(FOLDER_PROFILE_PICTURES).child(expert.getId()).getDownloadUrl().
                    addOnSuccessListener(
                            uri ->{
                              activity.getActivity().runOnUiThread(
                                      () ->
                                      {
                                          Glide.with(activity).load(uri).centerCrop().into(activity.getExpertPp());
                                      }
                              );
                            }
                    );

        }catch(Exception e)
        {
            Log.e(">>>", "There is no profile picture for: "+ expert.getLastName());
        }
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
