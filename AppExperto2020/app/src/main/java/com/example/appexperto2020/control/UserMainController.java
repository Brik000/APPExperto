package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;

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

public class UserMainController implements View.OnClickListener{

    @Getter
    private UserMainFragment activity;

    private HashMap<String, String> interests;
    private String session;
    private String folder;
    private String userName;
    private HashMap<String, Job> jobsFromServer;
    private ArrayList<Expert> expertsFromServer;


    public UserMainController(UserMainFragment activity, String session)
    {
        this.activity = activity;
        jobsFromServer = new HashMap<>();
        activity.getSearchView().setOnSearchClickListener(this);
        this.session = session;
        bringJobsFromServer();

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

    public void checkJob(Expert expert, HashMap<String, String> j)
    {
        Object[] keys  = j.keySet().toArray();
        HashMap<String,Job> jobs = expert.getJobList();
        ArrayList<Expert> experts = new ArrayList<>();
        for(int i = 0; i<keys.length;i++)
        {

            if(jobs != null && jobs.containsKey(keys[i]))
            {
                experts.add(expert);
                activity.getAdapter().addExpert(expert);
                activity.getAdapter().notifyDataSetChanged();

            }

            break;
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
        String[] selectedJobs= activity.getJobSpinner().getSelectedItemsAsString().split(", ");

        HashMap<String, String> jobsToSearch = new HashMap<>();
        for(int i = 0; i<selectedJobs.length;i++)
        {
            Job j = jobsFromServer.get(selectedJobs[i]);
            jobsToSearch.put(j.getId(), j.getId());
        }
        activity.getAdapter().removeData();
        searchCoincidences(jobsToSearch);

    }

    public void searchCoincidences( HashMap<String, String>  j){
        for(int i = 0; i<expertsFromServer.size();i++)
        {
            checkJob(expertsFromServer.get(i), j);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToBtn:
                String id = v.getContentDescription().toString();
                FragmentManager fm = activity.getActivity().getSupportFragmentManager();
                UserProfileFragment userProfileFragment = new UserProfileFragment(id,session);
                fm.beginTransaction().add(R.id.fragmentSelected, userProfileFragment, "temporal").commit();
                fm.beginTransaction().hide(fm.getPrimaryNavigationFragment()).commit();
                fm.beginTransaction().setPrimaryNavigationFragment(userProfileFragment).commit();
                fm.beginTransaction().show(userProfileFragment).commit();
            break;
            case R.id.searchView:
            {
                setupJobsSelected();
                break;
            }

        }
    }

    public void findExpertsByInterests() {

       expertsFromServer = new ArrayList<>();
        Query q = FirebaseDatabase.getInstance().getReference().child(FOLDER_EXPERTS);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Expert expert = d.getValue(Expert.class);
                    expertsFromServer.add(expert);
                    checkJob(expert, interests);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">>>>", databaseError.toString());

            }
        });
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
                activity.getJobSpinner().setItems(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

