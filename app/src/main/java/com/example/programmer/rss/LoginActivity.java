package com.example.programmer.rss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    // ui
    private EditText ed_email, ed_password;
    private Button btn_Login;
    private LoginButton loginButton;


    // var
    private String email, password;
    private static Boolean isEmail = false, isPass = false;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private Boolean isLogin = false;
    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inializeMAuth();

        createEditedText();


        createButton();


        inializefaceBook();

        createSharedPerfernce(getApplicationContext());


    }

    public static SharedPreferences createSharedPerfernce(Context context) {
        sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        return sharedPreferences;

    }

    private void inializeMAuth() {
        mAuth = FirebaseAuth.getInstance();


    }


    private void inializefaceBook() {
        loginButton = findViewById(R.id.login_button_s);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        // Creating CallbackManager
        callbackManager = CallbackManager.Factory.create();
        // Registering CallbackManager with the LoginButton
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("is_login", true);
                editor.commit();
                // Retrieving access token using the LoginResult
                AccessToken accessToken = loginResult.getAccessToken();
                handleFacebookAccessToken(accessToken);

                getUserProfile(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("errrrrrrrr", error.getMessage());

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserProfile(final AccessToken currentAccessToken) {


        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("is_login", true);
                            editor.putString("name",first_name+" "+last_name);
                            editor.putString("img_url",image_url);
                            editor.putString("email",email);
                            editor.commit();

                            Log.d("qqqq", first_name + " " + email + " " + id + " " + image_url);

                            mAuth.createUserWithEmailAndPassword(email, id).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    onBackPressed();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d("qqq", "handleFacebookAccessToken:" + token.getToken());

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCustomToken(token.getToken()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("qqqq", "signInWithCredential:success " + " yessss");
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {

                }

                // ...
            }
        });

    }


    private void createButton() {
        btn_Login = findViewById(R.id.btn_create_account_s);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = ed_email.getText().toString().trim();
                password = ed_password.getText().toString();


                if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ed_email.setBackground(getResources().getDrawable(R.drawable.bg_edittext_normal));
                    ed_email.setError("enter correct email syntax");
                    isEmail = false;
                } else {
                    ed_email.setBackground(getResources().getDrawable(R.drawable.bg_edittext_focused));
                    isEmail = true;
                }


                if (password.length() < 6) {
                    ed_password.setBackground(getResources().getDrawable(R.drawable.bg_edittext_normal));
                    ed_password.setError("password should be at least 6 char");
                    isPass = false;

                } else {
                    ed_password.setBackground(getResources().getDrawable(R.drawable.bg_edittext_focused));
                    isPass = true;
                }
                if (!isReady(isEmail, isPass)) {
                    return;

                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    isLogin = true;
                    editor.putBoolean("is_login", isLogin);
                    String[] n=email.split("@");
                    editor.putString("name",n[0]);
                    editor.putString("email",email);
                    editor.putString("img_url","");
                    editor.commit();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                                else
                                Toast.makeText(getApplicationContext(), "in correct email or password" +
                                        "" +
                                        "or check internet connection ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }


        });


    }

    private Boolean isReady(Boolean b1, Boolean b2) {

        if (b1 && b2) {
            btn_Login.setBackground(getResources().getDrawable(R.color.colorAccent));
            btn_Login.setEnabled(true);
            return true;
        } else {
            btn_Login.setBackground(getResources().getDrawable(R.color.gray_50));
            btn_Login.setEnabled(true);
            return false;
        }
    }

    private void createEditedText() {
        ed_email = findViewById(R.id.ed_email_s);
        ed_password = findViewById(R.id.ed_password_s);

        ed_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                email = textView.getText().toString();

                if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ed_email.setBackground(getResources().getDrawable(R.drawable.bg_edittext_normal));
                    ed_email.setError("enter correct email syntax");
                    isEmail = false;
                } else {
                    ed_email.setBackground(getResources().getDrawable(R.drawable.bg_edittext_focused));
                    isEmail = true;
                }
                isReady(isEmail, isPass);

                return false;
            }
        });

        ed_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                password = textView.getText().toString();

                if (password.length() < 6) {
                    ed_password.setBackground(getResources().getDrawable(R.drawable.bg_edittext_normal));
                    ed_password.setError("password should be at least 6 char");
                    isPass = false;

                } else {
                    ed_password.setBackground(getResources().getDrawable(R.drawable.bg_edittext_focused));
                    isPass = true;
                }
                isReady(isEmail, isPass);

                return false;
            }
        });
    }

    //Facebook login button
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();

        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
        }
    };


    public void gotoSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
