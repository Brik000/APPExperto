package com.example.appexperto2020.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.PhotosAdapter;
import com.example.appexperto2020.control.UserProfileController;
import com.google.firebase.auth.FirebaseAuth;

import lombok.Getter;

public class UserProfileFragment extends Fragment {
    @Getter
    private TextView nameTV,mailTV, jobTV, descriptionTV, phoneTV;
    @Getter
    private TextView serviceButton;
    @Getter
    private ImageView serviceBtn, expertPp;
    @Getter
    private RecyclerView photosRV;
    @Getter
    private PhotosAdapter adapter;

    @Getter
    private String uId;
    private UserProfileController controller;

    public UserProfileFragment(String id) {

        this.uId = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        serviceButton = view.findViewById(R.id.serviceButton);
        serviceBtn = view.findViewById(R.id.serviceBtn);
        nameTV = view.findViewById(R.id.nameTV);
        jobTV = view.findViewById(R.id.jobTV);
        mailTV = view.findViewById(R.id.mailTV);
        phoneTV = view.findViewById(R.id.phoneTV);
        descriptionTV = view.findViewById(R.id.descriptionTV);
        expertPp = view.findViewById(R.id.expertPp);

        photosRV = view.findViewById(R.id.photosRV);
        controller = new UserProfileController(this);

        adapter = new PhotosAdapter(controller);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        getPhotosRV().setLayoutManager(linearLayoutManager);
        getPhotosRV().setAdapter(adapter);

        if(uId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            getServiceButton().setVisibility(View.GONE);
        else getServiceButton().setVisibility(View.VISIBLE);
        return view;
    }
}
