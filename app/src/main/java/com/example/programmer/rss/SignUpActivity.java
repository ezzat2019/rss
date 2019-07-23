package com.example.programmer.rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.repositry.RepositryEmail;
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

public class SignUpActivity extends AppCompatActivity {

    private static Boolean isEmail = false, isPass = false, ischeck1 = false, isCheck2 = false;
    // ui
    private EditText ed_email, ed_password;
    private Button btn_create;
    private LoginButton loginButton;
    private CheckBox checkBox1, checkBox2;
    // var
    private String email, password;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private RepositryEmail repositryEmail;
    private Handler handler;
    private Runnable runnable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        inializeMAuth();

        createEditedText();

        createCheckBox();

        createButton();


        inializefaceBook();


        createButtonFace();

        sharedPreferences = LoginActivity.createSharedPerfernce(getApplicationContext());

        inalizeRep();

        createCheckStsn();


    }

    private void createCheckStsn() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                email = ed_email.getText().toString().trim();
                password = ed_password.getText().toString();


                if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ed_email.setBackground(getResources().getDrawable(R.drawable.bg_edittext_normal));

                    isEmail = false;
                } else {
                    ed_email.setBackground(getResources().getDrawable(R.drawable.bg_edittext_focused));
                    isEmail = true;
                }


                if (password.length() < 6) {
                    ed_password.setBackground(getResources().getDrawable(R.drawable.bg_edittext_normal));

                    isPass = false;

                } else {
                    ed_password.setBackground(getResources().getDrawable(R.drawable.bg_edittext_focused));
                    isPass = true;
                }
                isReady(isEmail, isPass, ischeck1, isCheck2);

                handler.postDelayed(runnable, 1000);

            }
        };


        handler.postDelayed(runnable, 1000);
    }

    private void inalizeRep() {
        repositryEmail = RepositryEmail.getInstance(getApplicationContext());
    }

    private void inializeMAuth() {
        mAuth = FirebaseAuth.getInstance();


    }

    private void createButtonFace() {

    }

    private void inializefaceBook() {
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        // Creating CallbackManager
        callbackManager = CallbackManager.Factory.create();
        // Registering CallbackManager with the LoginButton
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Retrieving access token using the LoginResult

                AccessToken accessToken = loginResult.getAccessToken();
                handleFacebookAccessToken(accessToken);

                getUserProfile(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this, "cancel", Toast.LENGTH_SHORT).show();

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

        onBackPressed();
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
                            editor.putString("name", first_name + " " + last_name);
                            editor.putString("img_url", image_url);
                            editor.putString("email", email);
                            editor.commit();
                            repositryEmail.insert(new ItemEmail(email, sharedPreferences.getInt("prefer", 0)));
                            Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();

                            mAuth.createUserWithEmailAndPassword(email, id).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


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

    private void createCheckBox() {
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ischeck1 = b;
                isReady(isEmail, isPass, ischeck1, isCheck2);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck2 = b;
                isReady(isEmail, isPass, ischeck1, isCheck2);
            }
        });
    }

    private void createButton() {
        btn_create = findViewById(R.id.btn_create_account);
        btn_create.setOnClickListener(new View.OnClickListener() {
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
                if (!isReady(isEmail, isPass, ischeck1, isCheck2)) {
                    return;

                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                Toast.makeText(SignUpActivity.this, "yes", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, "check internet connection", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }


        });


    }

    private Boolean isReady(Boolean b1, Boolean b2, Boolean b3, Boolean b4) {

        if (b1 && b2 && b3 && b4) {
            btn_create.setBackground(getResources().getDrawable(R.color.colorAccent));
            btn_create.setEnabled(true);
            return true;
        } else {
            btn_create.setBackground(getResources().getDrawable(R.color.gray_50));
            btn_create.setEnabled(true);
            return false;
        }
    }

    private void createEditedText() {
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);

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
                isReady(isEmail, isPass, ischeck1, isCheck2);

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
                isReady(isEmail, isPass, ischeck1, isCheck2);

                return false;
            }
        });
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }


}
