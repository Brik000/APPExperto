package com.example.appexperto2020.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.util.Constants;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static com.example.appexperto2020.util.Constants.SESSION_TYPE;

public class NavBarActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;

    private UserMainFragment userMainFragment;
    private UserProfileFragment userProfileFragment;
    private MyServicesFragment myServicesFragment;

    private String session;
    private ImageView logOutIV;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_feed);
        session = getIntent().getExtras().getString(SESSION_TYPE);
        logOutIV = findViewById(R.id.logOutIV);
        logOutIV.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.bottomNav);
        if (session.equals(Constants.SESSION_EXPERT)){
            bottomNavigationView.getMenu().removeItem(R.id.mainMenu);
            myServicesFragment = new MyServicesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentSelected, myServicesFragment, "myServices").commit();
            getSupportFragmentManager().beginTransaction().setPrimaryNavigationFragment(myServicesFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.servicesMenu);
            getSupportFragmentManager().beginTransaction().show(myServicesFragment).commit();
        }
        else {
            userMainFragment = new UserMainFragment(session);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentSelected, userMainFragment, "userMain").commit();
            getSupportFragmentManager().beginTransaction().setPrimaryNavigationFragment(userMainFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.mainMenu);
            getSupportFragmentManager().beginTransaction().show(userMainFragment).commit();
        }
        configureBottomNavBehavior();

    }

    private void configureBottomNavBehavior() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item ->{
                    Fragment fragmentActivity = null;
                    switch (item.getItemId()) {
                        case R.id.profileMenu:
                            if(userProfileFragment == null) {
                                userProfileFragment = new UserProfileFragment(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                getSupportFragmentManager().beginTransaction().add(R.id.fragmentSelected, userProfileFragment, "userProfile").commit();
                            }
                            fragmentActivity = userProfileFragment;
                            break;
                        case R.id.mainMenu:
                            fragmentActivity = userMainFragment;
                            break;
                        case R.id.servicesMenu:
                            if(myServicesFragment == null) {
                                myServicesFragment = new MyServicesFragment(session);
                                getSupportFragmentManager().beginTransaction().add(R.id.fragmentSelected, myServicesFragment, "myServices").commit();
                            }
                            fragmentActivity = myServicesFragment;
                            break;
                    }
                    if(fragmentActivity != null) {
                        if(getSupportFragmentManager().getPrimaryNavigationFragment().getTag().equals("temporal"))
                            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getPrimaryNavigationFragment()).commit();
                        else getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().getPrimaryNavigationFragment()).commit();
                        getSupportFragmentManager().beginTransaction().setPrimaryNavigationFragment(fragmentActivity).commit();
                        getSupportFragmentManager().beginTransaction().show(fragmentActivity).commit();
                    }
                    return true;
                });
    }

    public void logOutDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        signOut();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.sign_out_ask_message)).setPositiveButton(getString(R.string.agree), dialogClickListener)
                .setNegativeButton(R.string.no_answer, dialogClickListener).show();
    }

    public void signOut() {

        if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired())
            LoginManager.getInstance().logOut();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseAuth.getInstance().signOut();
        Intent i1 = new Intent(this, LoginActivity.class);
        i1.putExtra(SESSION_TYPE, session);
        startActivity(i1);
        Animatoo.animateDiagonal(this);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getPrimaryNavigationFragment().getTag().equals("temporal"))
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getPrimaryNavigationFragment()).commit();
        else getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().getPrimaryNavigationFragment()).commit();
        getSupportFragmentManager().beginTransaction().setPrimaryNavigationFragment(userMainFragment).commit();
        getSupportFragmentManager().beginTransaction().show(userMainFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logOutIV:
                logOutDialog();
                break;
        }
    }
}
