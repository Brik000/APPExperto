package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.view.UserProfileFragment;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.view.UserMainFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;

import static com.example.appexperto2020.util.Constants.FOLDER_CLIENTS;
import static com.example.appexperto2020.util.Constants.FOLDER_EXPERTS;
import static com.example.appexperto2020.util.Constants.SESSION_CLIENT;
import static com.example.appexperto2020.util.Constants.SESSION_EXPERT;

public class UserMainController implements View.OnClickListener, Switch.OnCheckedChangeListener {

    @Getter
    private UserMainFragment fragment;

    private HashMap<String, String> interests;
    private String session;
    private String folder;
    private String userName;
    private HashMap<String, Job> jobsFromServer;


    public UserMainController(UserMainFragment activity, String session)
    {
        this.fragment = activity;
        jobsFromServer = new HashMap<>();
        activity.getSwitchSearch().setOnCheckedChangeListener(this);
        this.session = session;
        bringJobsFromServer();

        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.HORIZONTAL));
        activity.getExpertsRV().addItemDecoration(new DividerItemDecoration(activity.getExpertsRV().getContext()
                , DividerItemDecoration.VERTICAL));

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(session.equals(SESSION_EXPERT))
            folder = FOLDER_EXPERTS;
        else folder = FOLDER_CLIENTS;

        FirebaseDatabase.getInstance().getReference().child(folder).child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userName = dataSnapshot.child("firstName").getValue(String.class);
                        activity.getWelcomeTV().setText(activity.getString(R.string.welcome_word)+" " + userName);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
                );

        if(session.equals(SESSION_CLIENT)){
            getInterests(uid);
            findExpertsByInterests();
        }



    }

    public void getInterests(String uid) {
        interests = new HashMap<>();

        FirebaseDatabase.getInstance().getReference().child("clients").child(uid).child("interests")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            Job job = d.getValue(Job.class);
                            Log.e("trae un interes", job.getName());
                            interests.put(job.getId(), job.getId());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

    }

    public void setupJobsSelected()
    {
        String[] selectedJobs= fragment.getJobSpinner().getSelectedItemsAsString().split(", ");

        HashMap<String, String> jobsToSearch = new HashMap<>();
        for(int i = 0; i<selectedJobs.length;i++)
        {
            Job j = jobsFromServer.get(selectedJobs[i]);
            jobsToSearch.put(j.getId(), j.getId());
        }
        fragment.getAdapter().removeData();
        findExpertsBySearch(jobsToSearch);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToBtn:
                String id = v.getContentDescription().toString();
                FragmentManager fm = fragment.getActivity().getSupportFragmentManager();
                UserProfileFragment userProfileFragment = new UserProfileFragment(id,session);
                fm.beginTransaction().add(R.id.fragmentSelected, userProfileFragment, "temporal").commit();
                fm.beginTransaction().hide(fm.getPrimaryNavigationFragment()).commit();
                fm.beginTransaction().setPrimaryNavigationFragment(userProfileFragment).commit();
                fm.beginTransaction().show(userProfileFragment).commit();
            break;
        }
    }

    public void findExpertsByInterests() {

        Query q = FirebaseDatabase.getInstance().getReference().child(FOLDER_EXPERTS);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fragment.getAdapter().removeData();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    checkJob(expert, interests);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">>>>", databaseError.toString());

            }
        });
    }

    public void findExpertsBySearch(HashMap<String, String> searched) {

        Query q = FirebaseDatabase.getInstance().getReference().child(FOLDER_EXPERTS);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    checkJob(expert, searched);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">>>>", databaseError.toString());

            }
        });
    }

    private void checkJob(Expert expert, HashMap<String, String> jobsParameter) {
        Iterable<String> jobs = expert.getJobList().keySet();
        for(String job : jobs)
        {
            if(jobs != null && jobsParameter.containsKey(job))
            {
                fragment.getAdapter().addExpert(expert);
                fragment.getAdapter().notifyDataSetChanged();
            }
            break;
        }
    }

    public void bringJobsFromServer(){

        Query q = FirebaseDatabase.getInstance().getReference().child("jobs");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> jobs = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Job j = d.getValue(Job.class);
                    jobsFromServer.put(j.getName(), j);
                    jobs.add(j.getName());
                }
                fragment.getJobSpinner().setItems(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchSearch:
                if (isChecked) {
                    if(fragment.getJobSpinner().getSelectedIndicies().isEmpty()) {
                        Toast.makeText(fragment.getActivity(), fragment.getString(R.string.no_choosen), Toast.LENGTH_LONG).show();
                        fragment.getSwitchSearch().setChecked(false);
                    }
                    else
                    setupJobsSelected();
                    fragment.getFindingByTV().setText(fragment.getString(R.string.finding_by_search));
                }
                else {
                    findExpertsByInterests();
                    fragment.getFindingByTV().setText(fragment.getString(R.string.finding_by_interests));
                }
        }
    }
}

