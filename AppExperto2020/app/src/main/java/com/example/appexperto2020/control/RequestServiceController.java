package com.example.appexperto2020.control;

import android.view.View;

import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.RequestServiceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RequestServiceController implements View.OnClickListener{

    private Service servicio;

    private String userId;

    private String expertId;

    private RequestServiceActivity view;

    private HTTPSWebUtilDomi utilDomi;

    public RequestServiceController(RequestServiceActivity view, String expertId ) {
        this.view = view;
        this.utilDomi = new HTTPSWebUtilDomi();

        this.view.getRequestBtn().setOnClickListener(this);
        this.view.getBackBtn().setOnClickListener(this);

        this.userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.expertId=expertId;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.butonreqser:
                this.generateService();
                FirebaseDatabase.getInstance().getReference().child("services").child(servicio.getId()).setValue(servicio);
            case R.id.gobackBtn:
                view.onBackPressed();
        }
    }

    public void generateService(){
         String clientId=userId;
         String description=view.getDescriptionTxt().getEditText().getText().toString();
         String expertId1=expertId;
         String id= FirebaseDatabase.getInstance().getReference().child("services").push().getKey();
         double reward= Double.parseDouble(view.getRewardTxt().getEditText().getText().toString());
         String title=view.getTitleTxt().getEditText().getText().toString();
        servicio=new Service(clientId,description,expertId1,id,reward,title, view.getString(R.string.service_pending));
    }
}
