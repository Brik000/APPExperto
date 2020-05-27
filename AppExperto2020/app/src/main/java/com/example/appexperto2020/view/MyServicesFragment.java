package com.example.appexperto2020.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.ServiceRecyclerViewAdapter;
import com.example.appexperto2020.control.ServiceController;
import com.example.appexperto2020.model.Service;

import java.util.ArrayList;

public class MyServicesFragment extends Fragment {

    private ArrayList<Service> services;
    private RecyclerView servicesRV;
    private ServiceController controller;
    private String actualSession;
    public MyServicesFragment(String session) {
        //Constructor donde puedes pasarle los parámetros que necesites desde NavbarActivity
        actualSession = session;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_services, container, false);
        controller = new ServiceController(this);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        servicesRV = view.findViewById(R.id.servicesRV);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        servicesRV.setLayoutManager(llm);
        servicesRV.setAdapter(controller.getAdapter());
    }

    public String getActualSession() {
        return actualSession;
    }
}
