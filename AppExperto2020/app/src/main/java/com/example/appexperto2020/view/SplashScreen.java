package com.example.appexperto2020.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appexperto2020.R;
import com.example.appexperto2020.view.MainActivity;

import java.security.MessageDigest;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Animatoo.animateZoom(this);
                    finish();
                }, 3000);
        Log.e("KeyHash", keyHash());
    }

    private String keyHash() {
        String keyHashes = null;
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.appexperto2020", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHashes = new String(Base64.encode(md.digest(), 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyHashes;
    }
}
