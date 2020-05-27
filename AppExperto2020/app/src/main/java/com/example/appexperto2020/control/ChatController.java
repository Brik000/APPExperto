package com.example.appexperto2020.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.MessagesAdapter;
import com.example.appexperto2020.model.Client;
import com.example.appexperto2020.model.Expert;
import com.example.appexperto2020.model.FCMMessage;
import com.example.appexperto2020.model.Message;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.util.Constants;
import com.example.appexperto2020.util.HTTPSWebUtilDomi;
import com.example.appexperto2020.util.NotificationUtils;
import com.example.appexperto2020.util.UtilDomi;
import com.example.appexperto2020.view.ChatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ChatController implements View.OnClickListener {

    private ChatActivity activity;
    private String username;
    private String chatroom;
    private String userRoot;
    private MessagesAdapter adapter;
    private User user;
    private Uri tempUri;

    public ChatController(ChatActivity activity) {
        this.activity = activity;
        adapter = new MessagesAdapter();
        activity.getMessagesList().setAdapter(adapter);

        username = activity.getIntent().getExtras().getString("username");
        chatroom = activity.getIntent().getExtras().getString("chatroom");
        userRoot = activity.getIntent().getExtras().getString("userRoot");

        activity.getSendBtn().setOnClickListener(this);
        activity.getGalBtn().setOnClickListener(this);

//        //Si es un objeto
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("user")
//                .child("KEFKEWMFL");
//        // Publisher - Subscriber
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                Log.e(">>>", "user: "+ user.getId());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //Obtener un objeto mediante un valor específico
        Query importante = FirebaseDatabase.getInstance().getReference().child(userRoot).child(username);
        importante.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    if (userRoot.equals("clients")) {
                        Map<String,Object> client = (Map<String,Object>) dataSnapshot.getValue();
                        createClient(client);
                        Log.e(">>>", user.getFirstName() + " " + user.getLastName());
                    } else {
                        user = dataSnapshot.getValue(Expert.class);
                        Log.e(">>>", user.getFirstName() + " " + user.getLastName());
                    }
                adapter.setUserId(user.getId());
                activity.getUsernameTV().setText(user.getLastName());

                Log.e(">>>", user.getId() + ":" + user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Si es una lista
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("chats").child(chatroom).limitToLast(10);
        // Publisher - Subscriber
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                adapter.addMessage(message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public ChatActivity getActivity() {
        return activity;
    }

    public void setActivity(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBtn:
                String body = activity.getMessageET().getText().toString();
                String pushid = FirebaseDatabase.getInstance().getReference()
                                .child("chats").child(chatroom).push().getKey();

                Message message = new Message( tempUri == null ? Constants.MESSAGE_TYPE_TEXT : Constants.MESSAGE_TYPE_IMAGE,
                        pushid,body,user.getId(), Calendar.getInstance().getTime().getTime());


                FCMMessage fcm = new FCMMessage();
                fcm.setTo("/topics/" + chatroom);
                fcm.setData(message);
                Gson gson = new Gson();
                String json = gson.toJson(fcm);

                new Thread(
                        ()->{
                            HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                            utilDomi.POSTtoFCM(Constants.FCM_API_KEY,json);
                        }
                ).start();

                if(tempUri != null){
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    storage.getReference().child("chats").child(message.getId())
                            .putFile(tempUri).addOnCompleteListener(
                      task -> {
                          if(task.isSuccessful()){
                              Log.e(">>>", "Foto subida con exito");
                              FirebaseDatabase.getInstance().getReference()
                                      .child("chats").child(chatroom).child(pushid).setValue(message);
                          }
                      }
                    );
                }
                else{
                    FirebaseDatabase.getInstance().getReference()
                            .child("chats").child(chatroom).child(pushid).setValue(message);
                }
                activity.hideImage();
                tempUri = null;

                break;
            case R.id.galBtn:
                Intent gal = new Intent((Intent.ACTION_GET_CONTENT));
                gal.setType("image/*");
                activity.startActivityForResult(gal, Constants.GALLERY_CALLBACK_CHAT);
        }
    }

    public void beforePause() {
        //Suscribirme a un topic
        FirebaseMessaging.getInstance().subscribeToTopic(chatroom).addOnCompleteListener(
                task->{
                    if (task.isSuccessful()){
                        Log.e(">>>","Suscrito");
                    }
                }
        );

    }

    public void beforeResume() {
        //Suscribirme a un topic
        FirebaseMessaging.getInstance().unsubscribeFromTopic(chatroom);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.GALLERY_CALLBACK_CHAT && resultCode == RESULT_OK){
             tempUri = data.getData();
             File file = new File(UtilDomi.getPath(activity,tempUri));
             Bitmap image = BitmapFactory.decodeFile(file.toString());
             activity.getMessageIV().setImageBitmap(image);
             activity.showImage();

        }
    }

    public void createClient(Map<String,Object> map){
        user = new Client();
        for (String key:map.keySet()) {
            switch (key){
                case "firstName":
                    user.setFirstName((String) map.get(key));
                    break;
                case "lastName":
                    user.setLastName((String) map.get(key));
                    break;
                case "password":
                    user.setPassword((String) map.get(key));
                    break;
                case "idDocument":
                    user.setIdDocument((String) map.get(key));
                    break;
                case "description":
                    user.setDescription((String) map.get(key));
                    break;
                case "id" :
                    user.setId((String) map.get(key));
                    break;
                case "email":
                    user.setEmail((String) map.get(key));
                    break;
            }
        }

    }


}
