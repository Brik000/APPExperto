package com.example.appexperto2020.control;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.core.content.FileProvider;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.JobsCustomAdapter;
import com.example.appexperto2020.adapter.PhotoCustomAdapter;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.Job;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.ExpertRegistrationActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ExpertRegistrationController implements View.OnClickListener, AdapterView.OnItemSelectedListener, HTTPSWebUtilDomi.OnResponseListener {

    public static final int CAMERA_CALLBACK = 1;
    private ExpertRegistrationActivity activity;
    private JobsCustomAdapter jobAdapter;
    private ArrayList<String> jobs;
    private PhotoCustomAdapter photoAdapter;
    private File file;
    private HTTPSWebUtilDomi httpsUtil;


    public ExpertRegistrationController(ExpertRegistrationActivity view){
        this.activity = view;
        this.httpsUtil = new HTTPSWebUtilDomi();
        httpsUtil.setListener(this);
        this.activity.getAddPhotoBut().setOnClickListener(this);
        this.activity.getRegisterBut().setOnClickListener(this);
        jobAdapter = new JobsCustomAdapter(this);
     //   this.activity.getJobList().setAdapter(jobAdapter);
        Job job = new Job();
        job.setName("");
        jobAdapter.addJob(job);
        jobs = new ArrayList<>();
        photoAdapter = new PhotoCustomAdapter();
        this.activity.getPhotoList().setAdapter(photoAdapter);
        File root = new File(view.getExternalFilesDir(null)+"");

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
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(this.activity.getExternalFilesDir(null)+"/photo"+photoAdapter.getCount()+".png");
                Uri photoUri = FileProvider.getUriForFile(this.activity, this.activity.getPackageName(), file);
                i.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                this.activity.startActivityForResult(i, CAMERA_CALLBACK);
                break;

            case R.id.registerBut:
                String j = activity.getJobSpinner().getSelectedItemsAsString();
                Gson gson = new Gson();

                new Thread(
                        ()-> {
                            Expert expert = new Expert(activity.getIdET().getText().toString(), activity.getNameExpertET().getText().toString(),
                                    activity.getPasswordET().getText().toString(), activity.getIdET().getText().toString(),
                                    activity.getCelularET().getText().toString(), activity.getEmailET().getText().toString(),
                                    j, activity.getDescriptionET().getText().toString());
                            String json = gson.toJson(expert);
                            httpsUtil.POSTrequest(Constants.REGISTER_EXPERT_CALLBACK, Constants.BD_URL_LOCAL_LH + Constants.EXPERTS_GROUP, json);
                        } ).start();

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

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK) {
                photoAdapter.addPhoto(file);

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
