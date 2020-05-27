package com.example.appexperto2020.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    @Getter
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progressBarMain);
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

      AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null && !accessToken.isExpired() || mAuth.getCurrentUser() != null) {
            Log.e(">>","Entra a login con facebook");
            logIn(mAuth.getCurrentUser().getUid());
        }
    }

    private void logIn(String userId) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child(Constants.FOLDER_CLIENTS).child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists())
                            FirebaseDatabase.getInstance().getReference().child(Constants.FOLDER_EXPERTS).child(userId).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            progressBar.setVisibility(View.GONE);
                                            if(AccessToken.getCurrentAccessToken() != null && !dataSnapshot.exists())
                                                registerWithFacebook();
                                            else if (dataSnapshot.exists())
                                                controller.goToUserMain(Constants.SESSION_EXPERT);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        else {
                            progressBar.setVisibility(View.GONE);
                            controller.goToUserMain(Constants.SESSION_CLIENT);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                                    logIn(user.getUid());
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
        params.putString("fields", "id,first_name,last_name,gender,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET, response -> {
                    if (response != null) {
                        try {
                            String profilePicUrl, firstName, lastName;
                            JSONObject data = response.getJSONObject();
                            Log.e("data graph request", data.toString());
                            if (data.has("picture")) {
                                profilePicUrl = data.getJSONObject(Constants.FACEBOOK_PP_URL).getJSONObject("data").getString("url");
                                Log.e("facebookPP", profilePicUrl);
                            } else profilePicUrl = null;
                            firstName = data.getString(Constants.FACEBOOK_FIRST_NAME);
                            lastName = data.getString(Constants.FACEBOOK_LAST_NAME);
                            controller.goToRegisterAfterFacebook(firstName,lastName,profilePicUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

            }
        }).executeAsync();
    }
}
