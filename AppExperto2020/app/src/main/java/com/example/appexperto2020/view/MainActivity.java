package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.MainController;

public class MainActivity extends AppCompatActivity {
    private TextView butRegisterMain;
    private Button butLoginClient;
    private Button butLoginWorker;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butRegisterMain = findViewById(R.id.butRegisterMain);
        butLoginClient = findViewById(R.id.butLoginClient);
        butLoginWorker = findViewById(R.id.butLoginWorker);
        controller = new MainController(this);
    }

    public TextView getButRegisterMain() {
        return butRegisterMain;
    }

    public Button getButLoginClient() {
        return butLoginClient;
    }

    public Button getButLoginWorker() {
        return butLoginWorker;
    }
}
