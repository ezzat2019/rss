package com.example.programmer.rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static Boolean isLogin = true;
    // var
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createSharedPref();

        createFirebaseAuth();

        createTextView();

        createImage();


    }

    private void createImage() {

        CircleImageView imageView = findViewById(R.id.img_profile);
        String url = sharedPreferences.getString("img_url", "");
        if (!url.equals("")) {
            Glide.with(getApplicationContext()).load(url).into(imageView);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.profile).into(imageView);

        }

    }

    private void createTextView() {
        TextView txt_email = findViewById(R.id.txt_email);
        // ui
        TextView txt_name = findViewById(R.id.txt_name);

        txt_name.setText(sharedPreferences.getString("name", "Name"));
        txt_email.setText(sharedPreferences.getString("email", "Email"));

    }

    private void createFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();

    }

    private void createSharedPref() {
        sharedPreferences = LoginActivity.createSharedPerfernce(getApplicationContext());
        isLogin = sharedPreferences.getBoolean("is_login", true);
    }

    public void logout(View view) {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        isLogin = false;
        editor.putBoolean("is_login", false);
        editor.commit();
        onBackPressed();
    }

    public void gotoPreferd(View view) {
        Intent intent = new Intent(getApplicationContext(), PreferActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
