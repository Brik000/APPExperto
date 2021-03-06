package com.example.appexperto2020.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.control.RequestServiceController;
import com.google.android.material.textfield.TextInputLayout;

import lombok.Getter;

public class RequestServiceActivity extends AppCompatActivity {
    @Getter
    private TextView maintitletxt;
    @Getter
    private TextInputLayout titleTxt,descriptionTxt, rewardTxt;
    @Getter
    private Button requestBtn ;
    @Getter
    private ImageButton backBtn;
    @Getter
    private ImageView serviceImg;
    @Getter
    private String expertId;

    private RequestServiceController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request_service);

        expertId = getIntent().getExtras().getString("idE");

        maintitletxt=findViewById(R.id.titleTV);
        titleTxt=findViewById(R.id.titleReqSer);
        descriptionTxt=findViewById(R.id.descriptionReqSer);
        rewardTxt=findViewById(R.id.rewardrtxt);
        requestBtn=findViewById(R.id.butonreqser);
        backBtn=findViewById(R.id.gobackBtn);
        serviceImg=findViewById(R.id.clientIV);
        controller=new RequestServiceController(this,getExpertId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }

}
