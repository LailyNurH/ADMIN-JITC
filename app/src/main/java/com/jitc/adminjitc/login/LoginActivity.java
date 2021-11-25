package com.jitc.adminjitc.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jitc.adminjitc.R;
import com.jitc.adminjitc.UI.MenuActivity;

public class LoginActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private SharedPrefManager sharedPrefManager;
    private EditText etUsername;
    private EditText etPassword;
    private TextView forgotpass;
    private Button btnLogin;
    private ProgressBar pbLogin;
    private ImageView ivLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sharedPrefManager = new SharedPrefManager(this);
        etUsername = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        pbLogin = findViewById(R.id.progressBar);
        ivLogin = findViewById(R.id.ivLogin);


        login();
    }

    private void login() {
        btnLogin.setOnClickListener(v -> {
            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();

            pbLogin.setVisibility(View.VISIBLE);
            ivLogin.setVisibility(View.GONE);

            if (username.isEmpty() || password.isEmpty()) {
                pbLogin.setVisibility(View.GONE);
                ivLogin.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Harap isi semua!", Toast.LENGTH_SHORT).show();
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        String spUsername = sharedPrefManager.getUsername();
                        String spPassword = sharedPrefManager.getPassword();

                        Log.d("username", "user" + username);
                        Log.d("password", "pass" + password);

                        if (username.equals(spUsername) && password.equals(spPassword)) {
                            Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                            Toast.makeText(LoginActivity.this, "Hai Admin!", Toast.LENGTH_SHORT).show();
                            sharedPrefManager.saveIsLogin(true);
                            finishAffinity();
                            startActivity(i);
                        } else {
                            pbLogin.setVisibility(View.GONE);
                            ivLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Username dan password salah!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000);
            }
        });
    }
}