package com.example.appexperto2020.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.appexperto2020.adapter.ServiceRecyclerViewAdapter;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.MyServicesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MyServicesController {
    private MyServicesFragment fragment;
    private ServiceRecyclerViewAdapter adapter;

    public MyServicesController(MyServicesFragment fragment) {
        this.fragment = fragment;
        getAdapterData();
    }

    public void getAdapterData() {
        adapter = new ServiceRecyclerViewAdapter(fragment);
        loadData();
    }

    public void loadData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query clientServiceQuery = FirebaseDatabase.getInstance().getReference().child("services").orderByChild("clientId").equalTo(userId);
        clientServiceQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Ninguna coincidencia
                if (dataSnapshot.getChildrenCount() == 0) {

                } else {
                    adapter.initializeServices();
                    for (DataSnapshot coincidence : dataSnapshot.getChildren()) {
                        Service s = coincidence.getValue(Service.class);
                        adapter.addService(s);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query expertServiceQuery = FirebaseDatabase.getInstance().getReference().child("services").orderByChild("expertId").equalTo(userId);
        expertServiceQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {

                } else {
                    for (DataSnapshot coincidence : dataSnapshot.getChildren()) {
                        Service s = coincidence.getValue(Service.class);
                        adapter.addService(s);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}