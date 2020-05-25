package com.example.appexperto2020.control;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appexperto2020.R;
import com.example.appexperto2020.adapter.MessagesAdapter;
import com.example.appexperto2020.model.Message;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.view.ChatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatController implements View.OnClickListener {

    private ChatActivity activity;
    private String username;
    private String chatroom;
    private MessagesAdapter adapter;
    private User user;

    public ChatController(ChatActivity activity) {
        this.activity = activity;
        adapter = new MessagesAdapter();
        activity.getMessagesList().setAdapter(adapter);

        username = activity.getIntent().getExtras().getString("username");
        chatroom = activity.getIntent().getExtras().getString("chatroom");

        activity.getSendBtn().setOnClickListener(this);

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
        Query importante = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);
        importante.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot coincidence: dataSnapshot.getChildren()){
                    user = coincidence.getValue(User.class);
                    adapter.setUserId(user.getId());
                    activity.getUsernameTV().setText(user.getIdDocument());
                    break;
                }
                Log.e(">>>", user.getId() + ":" + user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Si es una lista
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("chats").child(chatroom);
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
                Message message = new Message(pushid,body,user.getId(), Calendar.getInstance().getTime().getTime());
                FirebaseDatabase.getInstance().getReference()
                        .child("chats").child(chatroom).child(pushid).setValue(message);
        }
    }
}
