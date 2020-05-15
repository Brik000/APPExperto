package com.example.appexperto2020.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.PhotoCustomAdapter;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.UtilDomi;
import com.example.appexperto2020.view.RegisterActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK2;

public class RegisterController implements View.OnClickListener, HTTPSWebUtilDomi.OnResponseListener {

    public static final int CAMERA_CALLBACK = 1;
    private RegisterActivity activity;
    private PhotoCustomAdapter photoAdapter;
    private Uri uriPp;
    private ArrayList<Uri> uris;
    private HTTPSWebUtilDomi httpsUtil;

    private String session;
    private ArrayList<Job> jobsFromServer;

    public RegisterController(String session, RegisterActivity view){
        activity = view;
        this.session = session;
        jobsFromServer = new ArrayList<Job>();
        httpsUtil = new HTTPSWebUtilDomi();
        httpsUtil.setListener(this);
        activity.getAddPhotoBut().setOnClickListener(this);
        activity.getRegisterBut().setOnClickListener(this);
        activity.getAddPhotoIV().setOnClickListener(this);
        photoAdapter = new PhotoCustomAdapter();
        this.activity.getPhotoList().setAdapter(photoAdapter);
        File root = new File(view.getExternalFilesDir(null)+"");
        uris = new ArrayList<>();
        bringJobsFromServer();
    }


    private void bringJobsFromServer() {
        Query q = FirebaseDatabase.getInstance().getReference().child("jobs");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> jobs = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Job j = d.getValue(Job.class);

                    jobsFromServer.add(j);
                        Log.e(">>>", "job: "+ j.getName());

                    jobs.add(j.getName());
                }
                activity.getJobSpinner().setItems(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addPhotoBut:
                Intent gal = new Intent(Intent.ACTION_GET_CONTENT);
                gal.setType("image/*");
                activity.startActivityForResult(gal, GALLERY_CALLBACK);
                break;
            case R.id.addPhotoIV:
                Intent g = new Intent(Intent.ACTION_GET_CONTENT);
                g.setType("image/*");
                activity.startActivityForResult(g, GALLERY_CALLBACK2);
                break;
            case R.id.registerBut:
                //Poner condición de si el atributo session es Constants.SESSION_CLIENT, entonces registrar un Client o en caso contrario, un Expert
                String pushId =   FirebaseDatabase.getInstance().getReference().child("experts").push().getKey();
                Expert expert = buildExpert(pushId);
                FirebaseDatabase.getInstance().getReference().child("experts").child(pushId).setValue(expert);
                for (int i = 0; i < uris.size(); i++) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    storage.getReference().child("experts").child(expert.getId()).child("Doc"+i).putFile(uris.get(i)).addOnCompleteListener(
                            task -> {
                                Log.e(">>>>>>>", "photos were successfully uploaded");
                            }
                    );
                }

                addJobsToExpert(pushId);
                break;
        }
        }

    private Expert buildExpert(String pushId) {

        if(uriPp!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            storage.getReference().child("pps").child(pushId).putFile(uriPp);
        }


                String j = activity.getJobSpinner().getSelectedItemsAsString();
        String firstName = activity.getFistNameET().getText().toString();
        String lastName = activity.getLastNameET().getText().toString();

        String email = activity.getEmailET().getText().toString();
        String password = activity.getPasswordET().getText().toString();
        String description = activity.getDescriptionET().getText().toString();
        String idDocument = activity.getDocumentET().getText().toString();
        long cellphone = Long.parseLong(activity.getCelularET().getText().toString());
        String profilePicture = pushId;

      //  return new Expert(pushId, firstName,lastName, email, password, description, idDocument, profilePicture, cellphone, jobs);
          return new Expert(pushId, firstName,lastName, email, password, description, idDocument, profilePicture, cellphone);

    }

    public void addJobsToExpert(String pushId)
    {
        String[] selectedJobs= activity.getJobSpinner().getSelectedItemsAsString().split(",");
        List<Integer> selectedIndicies =  activity.getJobSpinner().getSelectedIndicies();
        for(int i = 0; i<selectedJobs.length;i++)
        {
            Job j = jobsFromServer.get(selectedIndicies.get(i));
            Job newJob = new Job();
            String idJob =   FirebaseDatabase.getInstance().getReference().child("experts").child(pushId).child("jobList").push().getKey();
            newJob.setId(j.getId());
            newJob.setName(selectedJobs[i]);
            FirebaseDatabase.getInstance().getReference().child("experts").child(pushId).child("jobList").child(idJob).setValue(newJob);
            Log.e(">>>>>>>>","Jobs register to expert");
            FirebaseDatabase.getInstance().getReference().child("jobs").child(j.getId()).child("experts").child(pushId).setValue(pushId);
            Log.e(">>>>>>>>","Expert register to job");

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode==GALLERY_CALLBACK && resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                uris.add(uri);
                File photo = new File(UtilDomi.getPath(this.activity, uri));
                photoAdapter.addPhoto(photo);
            }
        if (requestCode==GALLERY_CALLBACK2 && resultCode == RESULT_OK)
        {
            uriPp  = data.getData();
            File filePp = new File(UtilDomi.getPath(this.activity, uriPp));
            Bitmap i = BitmapFactory.decodeFile(filePp.getPath());
            Bitmap bitmap = Bitmap.createBitmap(i);
            activity.getSessionImage().setImageBitmap(bitmap);
        }
        }

    @Override
    public void onResponse(int callbackID, String response) {
        switch (callbackID){
            case Constants.REGISTER_EXPERT_CALLBACK:
                break;

        }
    }
}
