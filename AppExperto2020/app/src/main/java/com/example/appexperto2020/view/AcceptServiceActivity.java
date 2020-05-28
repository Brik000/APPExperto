package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.AcceptServiceController;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;

public class AcceptServiceActivity extends AppCompatActivity {

    private AcceptServiceController controller;
    private String actualSession;
    @Getter
    private TextView titleTV, serviceTV, descriptionTV, priceTV;
    @Getter
    private Button acceptBtn, refuseBtn;
    @Getter
    private ImageButton backBtn;
    @Getter
    private CircleImageView clientPP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_service);
        titleTV = findViewById(R.id.titleTV);
        serviceTV = findViewById(R.id.serviceTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        priceTV = findViewById(R.id.priceTV);
        acceptBtn = findViewById(R.id.acceptBtn);
        refuseBtn = findViewById(R.id.refuseBtn);
        backBtn = findViewById(R.id.backBtn);
        clientPP = findViewById(R.id.clientPP);
        controller = new AcceptServiceController(this);
    }
}
