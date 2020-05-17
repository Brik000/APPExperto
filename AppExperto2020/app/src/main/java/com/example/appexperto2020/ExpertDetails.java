package com.example.appexperto2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appexperto2020.control.ExpertDetailsController;

import org.w3c.dom.Text;

import lombok.Data;

@Data
public class ExpertDetails extends AppCompatActivity {
    private TextView expertDetailsNameTxt,expertDetailsLastNameTxt,expertDetailsDescriptionTxt,expertDetailsCellphoneTxt;
    private ListView expertDetailJobsList;
    private String id;
    private ExpertDetailsController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_details);

        expertDetailsNameTxt= findViewById(R.id.expertDetailsNameTxt);
        expertDetailsLastNameTxt= findViewById(R.id.expertDetailsLastNameTxt);
        expertDetailsDescriptionTxt = findViewById(R.id.expertDetailsDescriptionTxt);
        expertDetailsCellphoneTxt = findViewById(R.id.expertDetailsCellphoneTxt);

        expertDetailJobsList = findViewById(R.id.expertDetailJobsList);
        id = getIntent().getExtras().getString("id");
        controller = new ExpertDetailsController(this);
    }
}
