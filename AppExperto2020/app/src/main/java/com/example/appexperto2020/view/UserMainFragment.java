package com.example.appexperto2020.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.ExpertAdapter;
import com.example.appexperto2020.control.UserMainController;
import com.example.appexperto2020.util.MultiSelectionSpinner;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMainFragment extends Fragment {

    private UserMainController controller;
    private TextView welcomeTV;
    private RecyclerView expertsRV;
    @Getter
    private SearchView searchView;
    @Getter
    private ExpertAdapter adapter;
    @Getter
    private ProgressBar progressBar;
    @Getter
    private MultiSelectionSpinner jobSpinner;

    private String session;

    public UserMainFragment(String session) {
        this.session = session;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_main, container, false);
        welcomeTV = view.findViewById(R.id.welcomeTV);
        expertsRV = view.findViewById(R.id.expertsRV);
        progressBar = view.findViewById(R.id.progressBarList);
        controller = new UserMainController(this, session);
        adapter = new ExpertAdapter(controller);
        searchView = view.findViewById(R.id.searchView);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        getExpertsRV().setLayoutManager(linearLayoutManager);
        getExpertsRV().setAdapter(adapter);
        return view;
    }

    public TextView getWelcomeTV() {
        return welcomeTV;
    }

    public RecyclerView getExpertsRV() {
        return expertsRV;
    }

}
