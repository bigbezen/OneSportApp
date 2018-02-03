package com.example.matan.onesportapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matan.onesportapp.Cache.UserCache;
import com.example.matan.onesportapp.Util.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginWindow extends AppCompatActivity {

    private CallbackManager callbackManager;
    private ProgressDialog mDialog;
    private ImageView imgLogin;
    private SignInButton btmGoogleLogin;
    private TextView signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_window);

        this.callbackManager = CallbackManager.Factory.create();
        this.imgLogin = (ImageView)findViewById(R.id.ProfilePicture);
        this.signupButton = (TextView)findViewById(R.id.signupBtm);
        this.btmGoogleLogin = (SignInButton)findViewById(R.id.GoogleLoginButton);
        setGoogleButtonText(this.btmGoogleLogin,"Continue with Google");

        // Login to facebook
        LoginButton loginButton = (LoginButton)findViewById(R.id.FacebookLoginButton);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_birthday", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog((LoginWindow.this));
                mDialog.setMessage("Retrieving Data...");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();
                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        getFacebookData(object);

                        Intent intent = new Intent(getApplicationContext(), RegisterWindow.class);
                        startActivity(intent);
                        finish();
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        // Signup button
        this.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to register screen
                Intent intent = new Intent(getApplicationContext(), RegisterWindow.class);
                startActivity(intent);
                finish();
            }
        });

        // If the user already log in goto the main window
        if(AccessToken.getCurrentAccessToken() != null){
            Intent intent = new Intent(getApplicationContext(), MainAppWindow.class);
            startActivity(intent);
            finish();
        }
    }

    // Get from facebook the user data and create new user object
    private void  getFacebookData(JSONObject object){
        // Create the user from facebook data
        User user = new User();

        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=140&height=140");
            user.setUserProfilePictureUrl(profile_picture.toString());
            user.setUserFirstName(object.getString("first_name"));
            user.setUserLastName(object.getString("last_name"));
            user.setUserBirthDay(object.getString("birthday"));
            user.setUserGender(object.getString("gender"));
            user.setUserEmail(object.getString("email"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // Save the new user to the cache
            UserCache.setUser(user);
        }
    }

    private void printKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.matan.onesport", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void setGoogleButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setPadding(0, 0, 24, 0);
                tv.setTextSize(18);
                tv.setText(buttonText);
                return;
            }
        }
    }
}
