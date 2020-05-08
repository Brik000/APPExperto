package com.example.appexperto2020.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.JobsCustomAdapter;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.UtilDomi;
import com.example.appexperto2020.view.ExpertRegistrationActivity;
import com.example.appexperto2020.view.UsersMainActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.appexperto2020.util.Constants.GALLERY_CALLBACK;

public class ExpertRegistrationController implements View.OnClickListener, AdapterView.OnItemSelectedListener, HTTPSWebUtilDomi.OnResponseListener {

    public static final int CAMERA_CALLBACK = 1;
    private ExpertRegistrationActivity activity;
    private JobsCustomAdapter jobAdapter;
    private ArrayList<String> jobs;
    private ArrayList<File> photos;
    private PhotoCustomAdapter photoAdapter;
    private File file;
    private ArrayList<Uri> uris;
    private HTTPSWebUtilDomi httpsUtil;


    public ExpertRegistrationController(ExpertRegistrationActivity view){
        this.activity = view;
        this.httpsUtil = new HTTPSWebUtilDomi();
        httpsUtil.setListener(this);
        this.activity.getAddPhotoBut().setOnClickListener(this);
        this.activity.getRegisterBut().setOnClickListener(this);
        jobAdapter = new JobsCustomAdapter(this);
     //   this.activity.getJobList().setAdapter(jobAdapter);
        JobAdapter job = new JobAdapter("hola");
        jobAdapter.addJob(job);
        jobs = new ArrayList<>();
        photos = new ArrayList<>();
        photoAdapter = new PhotoCustomAdapter();
        this.activity.getPhotoList().setAdapter(photoAdapter);
        File root = new File(view.getExternalFilesDir(null)+"");
        uris = new ArrayList<>();
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

        activity.getJobSpinner().setItems(jobs);
        activity.getJobSpinner().setOnItemSelectedListener(this);
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
                String j = activity.getJobSpinner().getSelectedItemsAsString();
                String pushId =   FirebaseDatabase.getInstance().getReference().child("user").push().getKey();
                Expert expert = new Expert(pushId, activity.getNameExpertET().getText().toString(),
                        activity.getPasswordET().getText().toString(), activity.getIdET().getText().toString(),
                        activity.getCelularET().getText().toString(), activity.getEmailET().getText().toString(),
                        j, activity.getDescriptionET().getText().toString());
                Log.e(">>>>>>>>>>>> USER", expert.toString());
                FirebaseDatabase.getInstance().getReference().child("experts").child(pushId).setValue(expert);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String j = parent.getItemAtPosition(position).toString();
        jobs.add(j);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
