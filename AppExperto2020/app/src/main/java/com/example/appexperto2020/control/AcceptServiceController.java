package com.example.appexperto2020.control;

import android.view.View;

import com.example.appexperto2020.R;
import com.example.appexperto2020.view.AcceptServiceFragment;

public class AcceptServiceController implements View.OnClickListener {

    public AcceptServiceFragment fragment;
    public  AcceptServiceController(AcceptServiceFragment fragment)
    {
        this.fragment = fragment;
        fragment.getAcceptBtn().setOnClickListener(this);
        fragment.getRefuseBtn().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.acceptBtn:
            {
                ///SET THE SERVICE TO ACCEPTED
                break;
            }
            case R.id.refuseBtn:
            {
                ////SET THE SERVICE TO REFUSED
                break;
            }
        }

    }
}
