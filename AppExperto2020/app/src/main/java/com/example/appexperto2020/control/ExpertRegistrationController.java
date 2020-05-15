package com.example.appexperto2020.control;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.UtilDomi;
import com.example.appexperto2020.view.ExpertRegistrationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK;

public class ExpertRegistrationController implements View.OnClickListener, HTTPSWebUtilDomi.OnResponseListener {

    public static final int CAMERA_CALLBACK = 1;
    private ExpertRegistrationActivity activity;
    private ArrayList<File> photos;
    private PhotoCustomAdapter photoAdapter;
    private File file;
    private ArrayList<Uri> uris;
    private HTTPSWebUtilDomi httpsUtil;


    public ExpertRegistrationController(ExpertRegistrationActivity view){
        activity = view;
        httpsUtil = new HTTPSWebUtilDomi();
        httpsUtil.setListener(this);
        activity.getAddPhotoBut().setOnClickListener(this);
        activity.getRegisterBut().setOnClickListener(this);
     // this.activity.getJobList().setAdapter(jobAdapter);
        photos = new ArrayList<>();
        photoAdapter = new PhotoCustomAdapter();
        this.activity.getPhotoList().setAdapter(photoAdapter);
        File root = new File(view.getExternalFilesDir(null)+"");
        uris = new ArrayList<>();
        bringJobsFromServer();
//        Agregar Job
//        ArrayList<String> jobs = new ArrayList<>();
//        jobs.add("nuevoJob");
//        for (int i = 0; i<jobs.size();i++)
//        {
//            String pushId = FirebaseDatabase.getInstance().getReference().child("jobs").push().getKey();
//            Job job = new Job(pushId, jobs.get(i), null, null);
//            FirebaseDatabase.getInstance().getReference().child("jobs").child(pushId).setValue(job);
//        }

    }

    private void bringJobsFromServer() {
        //Alimentar desde base de datos
        Query q = FirebaseDatabase.getInstance().getReference().child("jobs");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> jobs = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Job j = d.getValue(Job.class);
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
            case R.id.registerBut:
                String pushId =   FirebaseDatabase.getInstance().getReference().child("user").push().getKey();
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
              //AQUI AGREGRARIAMOS A UNA RAMA DE JOB UN NUEVO EXPERTO (CON SU ID Y EL NOMBRE, O ALGO ASI)
                /**new Thread(
                        ()-> {

                            String[] jobs1 = j.split(",");
                            String json;
                            for(int q=0; q<jobs1.length;q++)
                            {
                                 json = gson.toJson(activity.getNameExpertET().getText().toString());
                                httpsUtil.POSTrequest(13, Constants.BD_URL_LOCAL_LH + Constants.EXPERTS_GROUP+"/"+jobs1[q], json);


                            }
                        }
                ).start();**/

                break;
        }
        }

    private Expert buildExpert(String pushId) {
        String j = activity.getJobSpinner().getSelectedItemsAsString();
        String name = activity.getNameExpertET().getText().toString();
        String email = activity.getEmailET().getText().toString();
        String password = activity.getPasswordET().getText().toString();
        String description = activity.getDescriptionET().getText().toString();
        String idDocument = activity.getDocumentET().getText().toString();
        long cellphone = Long.parseLong(activity.getCelularET().getText().toString());
        String profilePicture = "ruta de acceso a la foto en fireBaseStorage";
        HashMap jobs = new HashMap();
        jobs.put(j,j);
        return new Expert(pushId, name, name, email, password, description, idDocument, profilePicture, cellphone, jobs);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK) {

                PhotoAdapter photo = new PhotoAdapter(file);
                photos.add(file);

                photoAdapter.addPhoto(photo);

            }

            if (requestCode==GALLERY_CALLBACK && resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                uris.add(uri);
                File file = new File(UtilDomi.getPath(this.activity, uri));
                PhotoAdapter photo = new PhotoAdapter(file);
                photos.add(file);
                photoAdapter.addPhoto(photo);
            }
        }

    @Override
    public void onResponse(int callbackID, String response) {
        switch (callbackID){
            case Constants.REGISTER_EXPERT_CALLBACK:
                Log.d(">>>>","HOLA");
                break;

        }
    }
}
