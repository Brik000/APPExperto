package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appexperto2020.adapter.ExpertAdapter;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.view.UsersMainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserMainController implements View.OnClickListener{

    private UsersMainActivity activity;
    private ExpertAdapter expertAdapter;

    public UserMainController(UsersMainActivity activity)
    {
        this.activity = activity;
        String username = (String) activity.getIntent().getExtras().get("userName");
        Log.e(">>>>",username);
        String[] firtName = username.split(" ");
        activity.getWelcomeTV().setText("Bienvenid@ " +firtName[0]);
        ArrayList<Expert> experts = new ArrayList();

/**
        experts.add(new Expert("experto 1", "922", "abdc",  "Carpintero, cerrajero", "1107511808",
                "ruexpert@expert.com"));
        experts.add(new Expert("experto 2", "943", "abdc",  "Carpintero, cerrajero", "1107511808",
                "ruexpert@expert.com"));
        experts.add(new Expert("experto 3", "943", "abdc",  "Carpintero, cerrajero", "1107511808",
            "ruexpert@expert.com"));**/

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(activity, 2);
      //  linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activity.getExpertsRV().setLayoutManager(linearLayoutManager);
        expertAdapter = new ExpertAdapter(experts, this);
        activity.getExpertsRV().setAdapter(expertAdapter);

    }

    @Override
    public void onClick(View v) {

    }
}
