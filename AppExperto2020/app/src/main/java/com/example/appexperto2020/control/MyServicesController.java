package com.example.appexperto2020.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appexperto2020.adapter.ServiceRecyclerViewAdapter;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.MyServicesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
        String searchId;
        if(getFragment().getActualSession().equals(Constants.SESSION_CLIENT))
            searchId = "clientId";
        else searchId = "expertId";
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query userServiceQuery = FirebaseDatabase.getInstance().getReference().child("services").orderByChild(searchId).equalTo(userId);
        userServiceQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("new service", dataSnapshot.getKey());
                Service service = dataSnapshot.getValue(Service.class);
                adapter.addService(service);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}