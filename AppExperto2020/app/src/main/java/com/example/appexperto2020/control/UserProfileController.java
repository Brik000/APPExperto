package com.example.appexperto2020.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.view.RequestServiceActivity;
import com.example.appexperto2020.view.UserProfileFragment;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
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
    private UserProfileFragment fragment;
    private User user;
    private String jobs;
    private String folder;

    public UserProfileController(UserProfileFragment fragment, String session, String uId) {
        this.fragment = fragment;
        if(session.equals(Constants.SESSION_CLIENT) && uId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            folder = Constants.FOLDER_CLIENTS;
        else folder = FOLDER_EXPERTS;

        fragment.getServiceBtn().setOnClickListener(this);
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(folder)
                .child(fragment.getUId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(folder.equals(FOLDER_EXPERTS))
                    user = dataSnapshot.getValue(Expert.class);
                else user = dataSnapshot.getValue(Client.class);
                jobs = "";
                if(user instanceof Expert) {
                    Expert expert = (Expert) user;
                    for (Job j : expert.getJobList().values()) {
                        if (jobs.equals("")) {
                            jobs += j.getName();
                        } else {
                            jobs += " || " + j.getName();
                        }
                    }
                } else {
                    Client client = (Client) user;
                    for (Job j : client.getInterests().values()) {
                        if (jobs.equals("")) {
                            jobs += j.getName();
                        } else {
                            jobs += " || " + j.getName();
                        }
                    }
                }

                setProfilePicture();
                setJobPhotos();
                fragment.getActivity().runOnUiThread(
                        ()->{
                            fragment.getNameTV().setText(user.getFirstName() + " " + user.getLastName());
                            fragment.getDescriptionTV().setText(user.getDescription());
                            fragment.getJobTV().setText(jobs);
                            fragment.getMailTV().setText(user.getEmail());
                            if(user instanceof Expert) {
                                Expert expert = (Expert) user;
                                fragment.getPhoneTV().setText(expert.getCellphone() + "");
                            } else {
                                fragment.getPhoneTV().setVisibility(View.GONE);
                                fragment.getTxtCel().setVisibility(View.GONE);
                            }
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
        StorageReference listRef = storage.getReference().child(folder).child(user.getId());
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
        fragment.getAdapter().setIdExpert(user.getId());
        fragment.getAdapter().setData(uris);
    }
    public void setProfilePicture()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        try{
            storage.getReference().child(FOLDER_PROFILE_PICTURES).child(user.getId()).getDownloadUrl().
                    addOnSuccessListener(
                            uri ->{
                              fragment.getActivity().runOnUiThread(
                                      () ->
                                      {
                                          Glide.with(fragment).load(uri).centerCrop().into(fragment.getExpertPp());
                                      }
                              );
                            }
                    );

        }catch(Exception e)
        {
            Log.e(">>>", "There is no profile picture for: "+ user.getLastName());
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.serviceBtn:
                Intent i = new Intent(v.getContext(), RequestServiceActivity.class);
                Log.e("idE", user.getId());
                i.putExtra("idE", user.getId());
                v.getContext().startActivity(i);
                break;
        }
    }
}
