package com.example.appexperto2020.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.UtilDomi;
import com.example.appexperto2020.view.RegisterActivity;
import com.example.appexperto2020.view.UsersMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK_DOCS;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK_PP;

public class RegisterController implements View.OnClickListener {

    public static final int CAMERA_CALLBACK = 1;
    private RegisterActivity activity;
    private PhotoCustomAdapter photoAdapter;
    private Uri uriPp;
    private ArrayList<Uri> uris;
    private HTTPSWebUtilDomi httpsUtil;

    private String session;
    private HashMap<String, Job> jobsFromServer;

    public RegisterController(String session, RegisterActivity view){
        activity = view;
        this.session = session;
        jobsFromServer = new HashMap<>();
        httpsUtil = new HTTPSWebUtilDomi();
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

                    jobsFromServer.put(j.getName(), j);
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
                activity.startActivityForResult(gal, GALLERY_CALLBACK_DOCS);
                break;
            case R.id.addPhotoIV:
                Intent g = new Intent(Intent.ACTION_GET_CONTENT);
                g.setType("image/*");
                activity.startActivityForResult(g, GALLERY_CALLBACK_PP);
                break;
            case R.id.registerBut:
                DatabaseReference dBUser;
                StorageReference storageUser;
                if(session.equals(Constants.SESSION_CLIENT)) {
                    dBUser = FirebaseDatabase.getInstance().getReference().child(Constants.FOLDER_CLIENTS);
                    storageUser = FirebaseStorage.getInstance().getReference().child(Constants.FOLDER_CLIENTS);
                }
                else {
                    dBUser = FirebaseDatabase.getInstance().getReference().child(Constants.FOLDER_EXPERTS);
                    storageUser = FirebaseStorage.getInstance().getReference().child(Constants.FOLDER_EXPERTS);
                }
                String pushId = dBUser.push().getKey();
                User user = buildUser(pushId);

                dBUser.child(pushId).setValue(user);
                for (int i = 0; i < uris.size(); i++) {
                        storageUser.child(user.getId()).child("Doc"+i).putFile(uris.get(i)).addOnCompleteListener(
                                task -> {
                                    Log.e(">>>>>>>", "photos were successfully uploaded");
                                }
                        );
                    }
                setupJobsSelected(pushId, user.getFirstName(), dBUser);
                break;
        }
        }

    private User buildUser(String pushId) {

        if(uriPp!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            storage.getReference().child(Constants.FOLDER_PROFILE_PICTURES).child(pushId).putFile(uriPp);
        }
        String firstName = activity.getFistNameET().getText().toString();
        String lastName = activity.getLastNameET().getText().toString();
        String email = activity.getEmailET().getText().toString();
        String password = activity.getPasswordET().getText().toString();
        String description = activity.getDescriptionET().getText().toString();
        String idDocument = activity.getDocumentET().getText().toString();
        String profilePicture = pushId;
        if(session.equals(Constants.SESSION_CLIENT)) {
            return new Client(pushId, firstName, lastName, email, password, description, idDocument, null);
        }
        else {
            long cellphone = Long.parseLong(activity.getCelularET().getText().toString());
            return new Expert(pushId, firstName, lastName, email, password, description, idDocument, cellphone, null);
        }
    }

    public void setupJobsSelected(String pushId, String userName, DatabaseReference dBUser)
    {
        String[] selectedJobs= activity.getJobSpinner().getSelectedItemsAsString().split(", ");
        String jobType;
        String userType;
        if(session.equals(Constants.SESSION_CLIENT)){
            jobType = "interests";
            userType = Constants.FOLDER_CLIENTS;
        } else {
            jobType = "jobList";
            userType = Constants.FOLDER_EXPERTS;
        }
        for(int i = 0; i<selectedJobs.length;i++)
        {
            Job j = jobsFromServer.get(selectedJobs[i]);
            if(j != null) {
                Job newJob = new Job();
                newJob.setId(j.getId());
                newJob.setName(j.getName());
                dBUser.child(pushId).child(jobType).child(j.getId()).setValue(newJob);
                FirebaseDatabase.getInstance().getReference().child("jobs").child(j.getId()).child(userType).child(pushId).setValue(pushId);
                goToUserMain(userName);
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle(activity.getString(R.string.alert_title));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Vale",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                goToUserMain(userName);
                            }
                        });
                alertDialog.setMessage(activity.getString(R.string.no_choosen));
                alertDialog.show();
            }
        }
    }

    public void goToUserMain(String userName) {
        Intent i = new Intent(activity, UsersMainActivity.class);
        i.putExtra("userName", userName);
        i.putExtra(Constants.SESSION_TYPE, session);
        activity.startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode== GALLERY_CALLBACK_DOCS && resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                uris.add(uri);
                File photo = new File(UtilDomi.getPath(this.activity, uri));
                photoAdapter.addPhoto(photo);
            }
        if (requestCode== GALLERY_CALLBACK_PP && resultCode == RESULT_OK)
        {
            uriPp  = data.getData();
            File filePp = new File(UtilDomi.getPath(this.activity, uriPp));
            Bitmap i = BitmapFactory.decodeFile(filePp.getPath());
            Bitmap bitmap = Bitmap.createBitmap(i);
            activity.getSessionImage().setImageBitmap(bitmap);
        }
        }
}
