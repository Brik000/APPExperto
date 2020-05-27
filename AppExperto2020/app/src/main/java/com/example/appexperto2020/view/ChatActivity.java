package com.example.appexperto2020.view;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.ChatController;

public class ChatActivity extends AppCompatActivity {

    private TextView usernameTV;
    private ListView messagesList;
    private EditText messageET;
    private Button galBtn, sendBtn;
    private ConstraintLayout controlsContainer;
    private ImageView messageIV;
    private ChatController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        usernameTV = findViewById(R.id.usernameTV);
        messagesList = findViewById(R.id.messagesList);
        messageET = findViewById(R.id.messageET);
        galBtn = findViewById(R.id.galBtn);
        sendBtn = findViewById(R.id.sendBtn);
        messageIV = findViewById(R.id.messageIV);
        controlsContainer = findViewById(R.id.controlsContainer);
        controller = new ChatController(this);
    }

    //Mi activiad pierde el primer plano
    @Override
    protected void onPause() {
        super.onPause();
        controller.beforePause();
    }

    // Mi actividad retoma el primer plano
    @Override
    protected void onResume() {
        super.onResume();
        controller.beforeResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode,resultCode,data);
    }

    public TextView getUsernameTV() {
        return usernameTV;
    }

    public void setUsernameTV(TextView usernameTV) {
        this.usernameTV = usernameTV;
    }

    public ListView getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(ListView messagesList) {
        this.messagesList = messagesList;
    }

    public EditText getMessageET() {
        return messageET;
    }

    public void setMessageET(EditText messageET) {
        this.messageET = messageET;
    }

    public Button getGalBtn() {
        return galBtn;
    }

    public void setGalBtn(Button galBtn) {
        this.galBtn = galBtn;
    }

    public Button getSendBtn() {
        return sendBtn;
    }

    public void setSendBtn(Button sendBtn) {
        this.sendBtn = sendBtn;
    }

    public ConstraintLayout getControlsContainer() {
        return controlsContainer;
    }

    public void setControlsContainer(ConstraintLayout controlsContainer) {
        this.controlsContainer = controlsContainer;
    }

    public ImageView getMessageIV() {
        return messageIV;
    }

    public void setMessageIV(ImageView messageIV) {
        this.messageIV = messageIV;
    }

    public void hideImage(){
        messageIV.setVisibility(View.GONE);
    }

    public void showImage(){
        messageIV.setVisibility(View.VISIBLE);
    }
}
