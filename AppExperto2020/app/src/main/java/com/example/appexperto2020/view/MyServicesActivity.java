package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.ServiceRecyclerViewAdapter;
import com.example.appexperto2020.model.Service;

import java.util.ArrayList;

public class MyServicesActivity extends AppCompatActivity {

    private ArrayList<Service> services;
    private RecyclerView servicesRV;
    private ServiceRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        services = new ArrayList<>();
        Service temp = new Service();
        services.add(temp);
        services.add(temp);
        services.add(temp);

        initRecyclerView();
    }


    private void initRecyclerView(){
        servicesRV = findViewById(R.id.servicesRV);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        servicesRV.setLayoutManager(llm);
        adapter = new ServiceRecyclerViewAdapter(services,this);
        servicesRV.setAdapter(adapter);
    }
}
