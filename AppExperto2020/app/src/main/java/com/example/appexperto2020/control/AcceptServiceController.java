package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.view.AcceptServiceActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import static com.example.appexperto2020.util.Constants.FOLDER_PROFILE_PICTURES;
import static com.example.appexperto2020.util.Constants.FOLDER_SERVICES;

public class AcceptServiceController implements View.OnClickListener {

    public AcceptServiceActivity activity;
    public Service service;
    public String clientId;
    public String serviceId;
    public  AcceptServiceController(AcceptServiceActivity activity)
    {
        this.activity= activity;
        activity.getAcceptBtn().setOnClickListener(this);
        activity.getRefuseBtn().setOnClickListener(this);
        activity.getBackBtn().setOnClickListener(this);
         serviceId = activity.getIntent().getExtras().getString("service");
        String clientName =activity.getIntent().getExtras().getString("clientName");

        activity.getTitleTV().setText("SOLICITUD DE SERVICIO DE "+ clientName.toUpperCase());
        bringServiceFromServer(serviceId);
    }


    public void bringServiceFromServer(String serviceId)
    {
        Log.e("ENTRA ----->", serviceId);

        FirebaseDatabase.getInstance().getReference().child(FOLDER_SERVICES).child(serviceId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        Log.e("DATASNAPSHOT ----->", dataSnapshot.toString());
                                                         service = dataSnapshot.getValue(Service.class);
                                                         activity.runOnUiThread(
                                                                 ()->
                                                                 {
                                                                     activity.getDescriptionTV().setText(service.getDescription());
                                                                     activity.getServiceTV().setText(service.getTitle());
                                                                    activity.getPriceTV().setText("$ "+service.getReward());
                                                                    clientId = service.getClientId();
                                                                     setProfilePicture();

                                                                 }
                                                         );
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    }
                                                }
                );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.acceptBtn:
            {

                FirebaseDatabase.getInstance().getReference().child(FOLDER_SERVICES).child(serviceId).child("status").setValue(activity.getString(R.string.service_accepted));
                Log.e("-------->", "ACCEPTED");
                activity.onBackPressed();
                break;
            }
            case R.id.refuseBtn:
            {
                FirebaseDatabase.getInstance().getReference().child(FOLDER_SERVICES).child(serviceId).child("status").setValue(activity.getString(R.string.service_declined));
                Log.e("-------->", "REFUSED");
                activity.onBackPressed();
                break;
            }
            case R.id.backBtn:
            {
                activity.onBackPressed();
            }
        }

    }

    public void setProfilePicture()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        try{
            storage.getReference().child(FOLDER_PROFILE_PICTURES).child(clientId).getDownloadUrl().
                    addOnSuccessListener(
                            uri ->{
                                Log.e("URI", "LLEGO" );
                                activity.runOnUiThread(
                                        () ->
                                        {
                                           Glide.with(activity).load(uri).centerCrop().into(activity.getClientPP());
                                        }
                                );
                            }
                    );

        }catch(Exception e)
        {
           Log.e(">>>", "There is no profile picture for: "+ clientId);
        }
    }
}
