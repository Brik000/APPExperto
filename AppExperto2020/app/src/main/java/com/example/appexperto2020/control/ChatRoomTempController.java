package com.example.appexperto2020.control;

import android.content.Intent;
import android.view.View;

import com.example.appexperto2020.R;
import com.example.appexperto2020.model.User;
import com.example.appexperto2020.view.ChatActivity;
import com.example.appexperto2020.view.ChatRoomTempActivity;
import com.google.firebase.database.FirebaseDatabase;

public class ChatRoomTempController implements View.OnClickListener {

    private ChatRoomTempActivity activity;

    public ChatRoomTempController(ChatRoomTempActivity activity) {
        this.activity = activity;
        activity.getSigninBtn().setOnClickListener(this);

    }

    public ChatRoomTempActivity getActivity() {
        return activity;
    }

    public void setActivity(ChatRoomTempActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signinBtn:

                String chatroom = activity.getChatroomET().getText().toString();
                String username = activity.getUsernameET().getText().toString();

                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("chatroom",chatroom);
                activity.startActivity(intent);
                activity.finish();
                break;
        }
    }
}
