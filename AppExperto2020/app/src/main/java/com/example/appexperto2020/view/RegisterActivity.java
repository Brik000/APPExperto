package com.example.appexperto2020.view;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.RegisterController;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.MultiSelectionSpinner;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;

import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class RegisterActivity extends AppCompatActivity {

    private RegisterController controller;
    @Getter
    private CircleImageView sessionImage;
    @Getter
    private TextView iAmTV, addFilesTV;
    @Getter
    private ProgressBar progressBar;
    @Getter
    private TextInputLayout fistNameET, lastNameET, documentET, cellphoneET, emailET, descriptionET, passwordET, repeatPasswordET;
    @Getter
    private ImageView addPhotoBut;
    @Getter
    private GridView photoList;
    @Getter
    private Button registerBut;
    @Getter
    private MultiSelectionSpinner jobSpinner;
    @Getter
    private ImageView addPhotoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras = getIntent().getExtras();
        String session = extras.getString(SESSION_TYPE);
        fistNameET = findViewById(R.id.firstNameET);
        addPhotoIV = findViewById(R.id.addPhotoIV);
        fistNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        passwordET = findViewById(R.id.passwordET);
        repeatPasswordET = findViewById(R.id.repeatPasswordET);
        documentET = findViewById(R.id.documentET);
        cellphoneET = findViewById(R.id.cellphoneET);
        emailET = findViewById(R.id.mailET);
        addPhotoBut = findViewById(R.id.addPhotoBut);
        photoList = findViewById(R.id.photoList);
        registerBut = findViewById(R.id.registerBut);
        descriptionET = findViewById(R.id.descriptionET);
        jobSpinner = findViewById(R.id.jobSpinner);
        progressBar = findViewById(R.id.progressBarRegister);
        controller = new RegisterController(session,this);
        sessionImage = findViewById(R.id.sessionImage);
        iAmTV = findViewById(R.id.iAmTV);
        addFilesTV = findViewById(R.id.addFilesTV);

        if(session.equals(Constants.SESSION_CLIENT)) {
            sessionImage.setImageResource(R.drawable.client);
            iAmTV.setText(getString(R.string.register_client_title));
            addFilesTV.setText(R.string.addFilesClient);
            cellphoneET.setVisibility(View.GONE);
        } else {
            sessionImage.setImageResource(R.drawable.worker);
            iAmTV.setText(getString(R.string.register_expert_title));
            addFilesTV.setText(R.string.addFilesExpert);
            cellphoneET.setVisibility(View.VISIBLE);
        }
        if(extras.getString(Constants.FACEBOOK_FIRST_NAME) != null) {
            getFistNameET().getEditText().setText(extras.getString(Constants.FACEBOOK_FIRST_NAME));
            getFistNameET().setVisibility(View.GONE);
            getEmailET().setVisibility(View.GONE);
            getPasswordET().setVisibility(View.GONE);
            getRepeatPasswordET().setVisibility(View.GONE);
        }
        if(extras.getString(Constants.FACEBOOK_LAST_NAME) != null) {
            getLastNameET().getEditText().setText(extras.getString(Constants.FACEBOOK_LAST_NAME));
            getLastNameET().setVisibility(View.GONE);
        }
        if(extras.getString(Constants.FACEBOOK_PP_URL) != null) {
            Glide.with(this).load(extras.getString(Constants.FACEBOOK_PP_URL)).fitCenter().into(sessionImage);
            File f = new File(getExternalFilesDir(null)+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            new Thread(() -> {
                HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                utilDomi.saveURLImageOnFile(extras.getString(Constants.FACEBOOK_PP_URL), f);
                controller.setUriPp(Uri.fromFile(f));
            }).start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, RegisterTypeActivity.class);
        i.putExtras(getIntent());
        startActivity(i);
        Animatoo.animateCard(this);
    }
}
