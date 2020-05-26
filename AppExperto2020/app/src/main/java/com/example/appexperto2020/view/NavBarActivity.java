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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
        userMainFragment = new UserMainFragment(session);
//        getSupportFragmentManager().putFragment(getIntent(),"key",userMainFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSelected, userMainFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item ->{
                    Fragment fragmentActivity = null;
                    switch (item.getItemId()) {
                        case R.id.profileMenu:
                            if(userProfileFragment == null)
                        userProfileFragment = new UserProfileFragment(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            fragmentActivity = userProfileFragment;
                        break;
                        case R.id.mainMenu:
                            fragmentActivity = userMainFragment;
                            break;
                        case R.id.servicesMenu:
                            if(myServicesFragment == null)
                            myServicesFragment = new MyServicesFragment();
                            fragmentActivity = myServicesFragment;
                            break;
            }
                if(fragmentActivity != null) {
                    fragmentActivity.setRetainInstance(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentSelected, fragmentActivity).commit();
//                    getSupportFragmentManager().beginTransaction().show(myServicesFragment).commit();
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
        logOutDialog();
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
