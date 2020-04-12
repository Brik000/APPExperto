package com.example.appexperto2020.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.appexperto2020.R;
import com.example.appexperto2020.view.ExpertRegistrationActivity;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ExpertRegistrationController implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final int CAMERA_CALLBACK = 1;
    private ExpertRegistrationActivity view;
    private JobsCustomAdapter jobAdapter;
    private ArrayList<String> jobs;
    private ArrayList<File> photos;
    private PhotoCustomAdapter photoAdapter;
    private File file;


    public ExpertRegistrationController(ExpertRegistrationActivity view){
        this.view = view;

        this.view.getAddJobTxt().setOnClickListener(this);
        this.view.getAddPhotoBut().setOnClickListener(this);
        jobAdapter = new JobsCustomAdapter(this);
        this.view.getJobList().setAdapter(jobAdapter);
        JobAdapter job = new JobAdapter("hola");
        jobAdapter.addJob(job);
        jobs = new ArrayList<>();
        photos = new ArrayList<>();
        photoAdapter = new PhotoCustomAdapter();
        this.view.getPhotoList().setAdapter(photoAdapter);
        File root = new File(view.getExternalFilesDir(null)+"");
        Toast.makeText(this.view,root+"",Toast.LENGTH_LONG).show();



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addJobText:
                JobAdapter job = new JobAdapter("hola");

                jobAdapter.addJob(job);
                break;
            case R.id.addPhotoBut:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(this.view.getExternalFilesDir(null)+"/photo"+photos.size()+".png");
                Uri photoUri = FileProvider.getUriForFile(this.view, this.view.getPackageName(), file);
                i.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                this.view.startActivityForResult(i, CAMERA_CALLBACK);

        }
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK) {

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
}
