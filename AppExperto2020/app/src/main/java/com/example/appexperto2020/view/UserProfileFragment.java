package com.example.appexperto2020.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.UserProfileController;
import com.google.firebase.auth.FirebaseAuth;

import lombok.Getter;

public class UserProfileFragment extends Fragment {
    @Getter
    private TextView expertDetailsNameTxt,expertDetailsLastNameTxt,expertDetailsDescriptionTxt,expertDetailsCellphoneTxt;
    @Getter
    private ListView expertDetailJobsList;
    @Getter
    private Button serviceButton;
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
        expertDetailsNameTxt= view.findViewById(R.id.expertDetailsNameTxt);
        expertDetailsLastNameTxt= view.findViewById(R.id.expertDetailsLastNameTxt);
        expertDetailsDescriptionTxt = view.findViewById(R.id.expertDetailsDescriptionTxt);
        expertDetailsCellphoneTxt = view.findViewById(R.id.expertDetailsCellphoneTxt);
        expertDetailJobsList = view.findViewById(R.id.expertDetailJobsList);
        controller = new UserProfileController(this);
        if(uId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            getServiceButton().setVisibility(View.GONE);
        else getServiceButton().setVisibility(View.VISIBLE);
        return view;
    }
}
