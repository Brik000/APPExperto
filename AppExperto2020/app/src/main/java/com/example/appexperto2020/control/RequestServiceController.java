package com.example.appexperto2020.control;

import android.content.Intent;
import android.view.View;

import com.example.appexperto2020.R;
import com.example.appexperto2020.model.FCMMessage;
import com.example.appexperto2020.model.Message;
import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.RequestServiceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;

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
                FCMMessage fcm = new FCMMessage();
                fcm.setTo(Constants.FCM_TO_EXPERT_SERVICE + servicio.getExpertId());
                Message m = new Message(Constants.MESSAGE_TYPE_TEXT,"Service",
                        "Se ha solicitado un nuevo servicio.",
                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        Calendar.getInstance().getTime().getTime());
                fcm.setData(m);
                Gson gson = new Gson();
                String json = gson.toJson(fcm);
                new Thread(
                        ()->{
                          HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                          utilDomi.POSTtoFCM(Constants.FCM_API_KEY,json);
                        }
                ).start();

                view.onBackPressed();
                break;
            case R.id.gobackBtn:
                view.onBackPressed();
                break;
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
