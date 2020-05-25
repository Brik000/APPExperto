package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ChatRoomTempController;

public class ChatRoomTempActivity extends AppCompatActivity {

    private EditText chatroomET,usernameET;
    private Button signinBtn;
    private ChatRoomTempController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_temp);
        chatroomET =  findViewById(R.id.chatroomET);
        usernameET = findViewById(R.id.usernameET);
        signinBtn = findViewById(R.id.signinBtn);

        controller = new ChatRoomTempController(this);

    }

    public EditText getChatroomET() {
        return chatroomET;
    }

    public void setChatroomET(EditText chatroomET) {
        this.chatroomET = chatroomET;
    }

    public EditText getUsernameET() {
        return usernameET;
    }

    public void setUsernameET(EditText usernameET) {
        this.usernameET = usernameET;
    }

    public Button getSigninBtn() {
        return signinBtn;
    }

    public void setSigninBtn(Button signinBtn) {
        this.signinBtn = signinBtn;
    }
}
