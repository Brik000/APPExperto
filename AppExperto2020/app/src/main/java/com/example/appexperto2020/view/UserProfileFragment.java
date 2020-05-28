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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.PhotosAdapter;
import com.example.appexperto2020.control.UserProfileController;
import com.google.firebase.auth.FirebaseAuth;

import lombok.Getter;

public class UserProfileFragment extends Fragment {

    private final String session;
    @Getter
    private TextView nameTV,mailTV, jobTV, descriptionTV, phoneTV, txtCel;
    @Getter
    private TextView serviceText;
    @Getter
    private ImageView serviceBtn, expertPp;
    @Getter
    private RecyclerView photosRV;
    @Getter
    private PhotosAdapter adapter;

    @Getter
    private String uId;

    private UserProfileController controller;

    public UserProfileFragment(String id, String session) {
        this.uId = id;
        this.session = session;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        serviceText = view.findViewById(R.id.serviceTxtBtn);
        serviceBtn = view.findViewById(R.id.serviceBtn);
        nameTV = view.findViewById(R.id.nameTV);
        jobTV = view.findViewById(R.id.jobTV);
        mailTV = view.findViewById(R.id.mailTV);
        phoneTV = view.findViewById(R.id.phoneTV);
        txtCel = view.findViewById(R.id.txtCelLeft);
        descriptionTV = view.findViewById(R.id.descriptionTV);
        expertPp = view.findViewById(R.id.expertPp);

        photosRV = view.findViewById(R.id.photosRV);
        controller = new UserProfileController(this, session, uId);

        adapter = new PhotosAdapter(controller);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        getPhotosRV().setLayoutManager(linearLayoutManager);
        getPhotosRV().setAdapter(adapter);

        if(uId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            getServiceBtn().setVisibility(View.GONE);
            getServiceText().setVisibility(View.GONE);
        }
        else{
            getServiceText().setVisibility(View.VISIBLE);
            getServiceBtn().setVisibility(View.VISIBLE);
        }
        return view;
    }
}
