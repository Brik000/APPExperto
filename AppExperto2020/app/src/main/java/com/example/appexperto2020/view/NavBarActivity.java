package com.example.appexperto2020.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.appexperto2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavBarActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_feed);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemReselectedListener(
                item ->{
                    Fragment fragmentActivity = null;
                    switch (item.getItemId()) {
                        case R.id.profileMenu:
                    //fragmentActivity = new UserProfileActivity();
                    break;
                        case R.id.mainMenu:
                            //fragmentActivity = new UserMainActivity();
                            break;
                        case R.id.servicesMenu:
                            fragmentActivity = new ServicesListFragment();
                            break;
            }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSelected, fragmentActivity).commit();
                });
    }
}
