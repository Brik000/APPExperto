package com.example.appexperto2020.control;

import android.widget.BaseAdapter;

public class JobAdapter   {


    private String job;

    public JobAdapter(String job)
    {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
