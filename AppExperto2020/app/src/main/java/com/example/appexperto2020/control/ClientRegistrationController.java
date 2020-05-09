package com.example.appexperto2020.control;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.MultiSelectionSpinner;
import com.example.appexperto2020.view.ClientRegistrationActivity;
import com.example.appexperto2020.view.MainActivity;
import com.example.appexperto2020.view.UsersMainActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;

public class ClientRegistrationController implements View.OnClickListener, HTTPSWebUtilDomi.OnResponseListener{

    private Client usuario;

    private ClientRegistrationActivity view;

    private HTTPSWebUtilDomi utilDomi;




    public ClientRegistrationController(ClientRegistrationActivity view) {
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
                            utilDomi.POSTrequest(Constants.REGISTER_USER_CALLBACK,Constants.BD_URL_LOCAL_LH+Constants.USERS_GROUP,json);
                        }
                ).start();
                Intent reg = new Intent(view, MainActivity.class);
                view.startActivity(reg);
                Animatoo.animateFade(view);
        break;

        }

    }


    public void GenerateUser() {

        String pushId = FirebaseDatabase.getInstance().getReference().child("user").push().getKey();
        String Nombre = view.getTxtNombreApellido().getText().toString();
        String Usuario = view.getTxtUserName().getText().toString();
        String Mail = view.getTxtEmailUser().getText().toString();
        String Contrasena = view.getTxtPassword().getText().toString();
        String Descripcion = view.getTxtDescription().getText().toString();
        String intereses = ((MultiSelectionSpinner) view.getSpinnerIntereses()).getSelectedItemsAsString();
        HashMap hashMap = new HashMap();
        hashMap.put(intereses, intereses);
        usuario = new Client(pushId, Nombre, Usuario, Mail, Contrasena, Descripcion, intereses, "ruta foto", hashMap);

    }

    @Override
    public void onResponse(int callbackID, String response) {
        switch(callbackID){
            case Constants.REGISTER_USER_CALLBACK:
                Log.d("ZZZZZZZZZZZZZZZZZZZZ", "ENTROOOOOOOOO");

                Intent i = new Intent(view, UsersMainActivity.class);
                i.putExtra("userName", view.getTxtNombreApellido().getText().toString());
                view.startActivity(i);
                break;
        }
    }
}
