package com.example.appexperto2020.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appexperto2020.R;
import com.example.appexperto2020.control.MainController;
import com.example.appexperto2020.util.Constants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONObject;

import java.util.Arrays;

import lombok.Getter;

public class MainActivity extends AppCompatActivity {
    @Getter
    private TextView butRegisterMain;
    @Getter
    private Button butLoginClient;
    @Getter
    private Button butLoginWorker;

    private MainController controller;
    private CallbackManager callbackManager;
    @Getter
    private LoginButton butFacebook;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butRegisterMain = findViewById(R.id.butRegisterMain);
        butLoginClient = findViewById(R.id.butLoginClient);
        butLoginWorker = findViewById(R.id.butLoginWorker);
        butFacebook = findViewById(R.id.login_button);
        controller = new MainController(this);
        mAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();

        getButFacebook().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("success", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("cancel", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("error de feis", "facebook:onError", error);
                // ...
            }
        });

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        if(isLoggedIn)
//            controller.goToUserMain();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,
                        task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("<<", "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithCredential:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                        // ...
                });
    }

    private void registerWithFacebook() {
        Bundle params = new Bundle();
        params.putString("fields", "id,name,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET, response -> {
                    if (response != null) {
                        try {
                            JSONObject data = response.getJSONObject();
                            if (data.has("picture")) {
                                String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                Log.e("linkFacebookPP", profilePicUrl);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

            }
        }).executeAsync();
    }
}
