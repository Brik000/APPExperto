package com.example.appexperto2020.control;

import android.view.View;

import com.example.appexperto2020.model.Service;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.view.RequestServiceActivity;

public class RequestServiceController implements View.OnClickListener{

    private Service servicio;

    private RequestServiceActivity view;

    private HTTPSWebUtilDomi utilDomi;

    public RequestServiceController(RequestServiceActivity view) {
        this.view = view;
        this.utilDomi = new HTTPSWebUtilDomi();
        this.view
    }

    @Override
    public void onClick(View v) {

    }
}
