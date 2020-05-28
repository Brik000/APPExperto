package com.example.appexperto2020.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.AcceptServiceController;

import lombok.Getter;

public class AcceptServiceFragment extends Fragment {

    private AcceptServiceController controller;
    private String actualSession;
    @Getter
    private TextView titleTV, serviceTV, descriptionTV, priceTV;
    @Getter
    private Button acceptBtn, refuseBtn;

    public AcceptServiceFragment(String session) {
        actualSession = session;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accept_service, container, false);
        titleTV = view.findViewById(R.id.titleTV);
        serviceTV = view.findViewById(R.id.serviceTV);
        descriptionTV = view.findViewById(R.id.descriptionTV);
        priceTV = view.findViewById(R.id.priceTV);
        acceptBtn = view.findViewById(R.id.acceptBtn);
        refuseBtn = view.findViewById(R.id.refuseBtn);
        controller = new AcceptServiceController(this);
        return view;
    }


    public String getActualSession() {
        return actualSession;
    }
}
