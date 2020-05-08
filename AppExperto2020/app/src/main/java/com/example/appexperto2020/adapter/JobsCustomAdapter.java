package com.example.appexperto2020.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ExpertRegistrationController;
import com.example.appexperto2020.control.JobAdapter;

import java.util.ArrayList;

public class JobsCustomAdapter extends BaseAdapter {

    private ArrayList<JobAdapter> jobs;
    private ExpertRegistrationController controller;

    public JobsCustomAdapter(ExpertRegistrationController controller){
        jobs = new ArrayList<>();
        this.controller = controller;
    }

    @Override
    public int getCount() {
        return jobs.size();
    }

    @Override
    public Object getItem(int i) {
        return jobs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.job_line, null);
        Spinner jobSpinner = row.findViewById(R.id.jobSpinner);

        ArrayList<String> jobs = new ArrayList<>();
        //Alimentar desde base de datos
        jobs.add("Agente de bolsa");
        jobs.add("Arquitect");
        jobs.add("Bailarin");
        jobs.add("Bartender");
        jobs.add("Carpintero");
        jobs.add("Cantante");
        jobs.add("Carpintero");
        jobs.add("Cocinero");
        jobs.add("Conductor");
        jobs.add("Contador");
        jobs.add("Planeador de eventos");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent.getContext(), R.layout.support_simple_spinner_dropdown_item,
                jobs);
        jobSpinner.setAdapter(adapter);
        jobSpinner.setOnItemSelectedListener(this.controller);

        return row;
    }

    public void addJob(JobAdapter job)
    {
        jobs.add(job);
        this.notifyDataSetChanged();

    }
}



