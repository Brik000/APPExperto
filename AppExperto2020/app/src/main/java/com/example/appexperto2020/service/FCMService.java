package com.example.appexperto2020.service;

import android.util.Log;

import com.example.appexperto2020.model.Message;
import com.example.appexperto2020.util.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(">>>","" + remoteMessage.getData());
        JSONObject object = new JSONObject(remoteMessage.getData());
        Gson gson = new Gson();
        Message message = gson.fromJson(object.toString(), Message.class);
        Log.e(">>> FCM", message.getId());
        if (message.getId().equals("Service")) {
            NotificationUtils.createNotificationServices(this,message.getBody());
        }
        else{
            NotificationUtils.createNotification(this,message.getBody());
        }


    }
}
