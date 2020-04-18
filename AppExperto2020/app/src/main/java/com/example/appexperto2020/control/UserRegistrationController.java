package com.example.appexperto2020.control;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.appexperto2020.R;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.MultiSelectionSpinner;
import com.example.appexperto2020.view.CreateUserActivity;
import com.google.gson.Gson;

public class UserRegistrationController implements View.OnClickListener{

    private User usuario;

    private CreateUserActivity view;

    private HTTPSWebUtilDomi utilDomi;




    public UserRegistrationController(CreateUserActivity view) {
        this.view = view;
        utilDomi=new HTTPSWebUtilDomi();
        this.view.getBtnNewUser().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.butNewUser:

                new Thread(
                        ()->{
                            Gson gson=new Gson();
                            GenerateUser();
                            String json=gson.toJson(usuario);
                            utilDomi.POSTrequest(Constants.REGISTER_USER_CALLBACK,Constants.BD_URL+Constants.USERS_GROUP,json);
                        }
                ).start();
        break;

        }

    }


    public void GenerateUser(){

        String Nombre=view.getTxtNombreApellido().getText().toString();
        String Usuario=view.getTxtUserName().getText().toString();
        String Mail=view.getTxtEmailUser().getText().toString();
        String Contrasena=view.getTxtPassword().getText().toString();
        String Descripcion=view.getTxtDescription().getText().toString();
        String intereses=((MultiSelectionSpinner)view.getSpinnerIntereses()).getSelectedItemsAsString();

         usuario=new User(Nombre,Usuario,Mail,Contrasena,Descripcion,intereses);




    }
}
