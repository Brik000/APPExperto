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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.PhotoCustomAdapter;
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.UtilDomi;
import com.example.appexperto2020.view.NavBarActivity;
import com.example.appexperto2020.view.RegisterActivity;
import com.example.appexperto2020.view.UserMainFragment;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
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

import lombok.Setter;

import static android.app.Activity.RESULT_OK;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK_DOCS;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK_PP;
import static com.example.appexperto2020.util.Constants.SESSION_EXPERT;
import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class RegisterController implements View.OnClickListener {

    private RegisterActivity activity;
    private PhotoCustomAdapter photoAdapter;
    @Setter
    private Uri uriPp;
    private ArrayList<Uri> uris;

    private String session;
    private HashMap<String, Job> jobsFromServer;

    public RegisterController(String session, RegisterActivity view){
        activity = view;
        this.session = session;
        jobsFromServer = new HashMap<>();
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
                String[] selectedJobs= activity.getJobSpinner().getSelectedItemsAsString().split(", ");
                if(AccessToken.getCurrentAccessToken() != null && (activity.getDocumentET().getEditText().getText().toString().trim().isEmpty()
                        || (session.equals(SESSION_EXPERT) && activity.getCellphoneET().getEditText().getText().toString().length()<1) || jobsFromServer.get(selectedJobs[0]) == null)) {
                    Toast.makeText(activity, activity.getString(R.string.fill_blank_spaces), Toast.LENGTH_LONG).show();
                    return;
                }
                if(AccessToken.getCurrentAccessToken() == null && (activity.getFistNameET().getEditText().getText().toString().trim().isEmpty()
                        || activity.getLastNameET().getEditText().getText().toString().trim().isEmpty()
                        || activity.getDocumentET().getEditText().getText().toString().trim().isEmpty()
                        || activity.getEmailET().getEditText().getText().toString().trim().isEmpty()
                        || activity.getPasswordET().getEditText().getText().toString().trim().isEmpty()
                        || (session.equals(SESSION_EXPERT) && activity.getCellphoneET().getEditText().getText().toString().length()<1)
                        || jobsFromServer.get(selectedJobs[0]) == null)){
                    Toast.makeText(activity, activity.getString(R.string.fill_blank_spaces), Toast.LENGTH_LONG).show();
                    return;
                } if (!activity.getPasswordET().getEditText().getText().toString().trim().equals(
                    activity.getRepeatPasswordET().getEditText().getText().toString().trim())) {
                Toast.makeText(activity, activity.getString(R.string.password_repeat_wrong), Toast.LENGTH_LONG).show();
                return;
            }
                activity.getRegisterBut().setEnabled(false);
                activity.getProgressBar().setVisibility(View.VISIBLE);
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
                User user = buildUser();
                if (AccessToken.getCurrentAccessToken() == null)
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnSuccessListener(
                            (authResult) -> {
                                registerInDataBase(user,dBUser,storageUser);
                            }
                    ).addOnFailureListener(
                            (e) -> {
                                loadingFinished();
                                Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                    );
                else {
                    Log.e(">>","Ingresa datos en DB con auth de facebook");
                    registerInDataBase(user, dBUser, storageUser);
                }
                break;
        }
    }

    private void registerInDataBase(User user, DatabaseReference dBUser, StorageReference storageUser) {
        loadingFinished();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user.setId(id);
        if(uriPp!= null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            storage.getReference().child(Constants.FOLDER_PROFILE_PICTURES).child(id).putFile(uriPp);
        }
        dBUser.child(id).setValue(user);
        for (int i = 0; i < uris.size(); i++) {
            storageUser.child(user.getId()).child("Doc"+i).putFile(uris.get(i)).addOnCompleteListener(
                    task -> {
                        Log.e(">>>>>>>", "photos were successfully uploaded");
                    }
            );
        }
        setupJobsSelected(id, user.getFirstName(), dBUser);
    }

    private void loadingFinished() {
        activity.getRegisterBut().setEnabled(true);
        activity.getProgressBar().setVisibility(View.GONE);
    }

    private User buildUser() {
        String firstName = activity.getFistNameET().getEditText().getText().toString();
        String lastName = activity.getLastNameET().getEditText().getText().toString();
        String email = activity.getEmailET().getEditText().getText().toString();
        String password = activity.getPasswordET().getEditText().getText().toString();
        String description = activity.getDescriptionET().getEditText().getText().toString();
        String idDocument = activity.getDocumentET().getEditText().getText().toString();
        if(session.equals(Constants.SESSION_CLIENT)) {
            return new Client(null, firstName, lastName, email, password, description, idDocument, null);
        }
        else {
            long cellphone = Long.parseLong(activity.getCellphoneET().getEditText().getText().toString());
            return new Expert(null, firstName, lastName, email, password, description, idDocument, cellphone, null);
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
                goToUserMain();
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle(activity.getString(R.string.alert_title));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, activity.getString(R.string.agree),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                goToUserMain();
                            }
                        });
                alertDialog.setMessage(activity.getString(R.string.no_choosen));
                alertDialog.show();
            }
        }
    }

    public void goToUserMain() {
        Intent i = new Intent(activity, NavBarActivity.class);
        i.putExtra(SESSION_TYPE, session);
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