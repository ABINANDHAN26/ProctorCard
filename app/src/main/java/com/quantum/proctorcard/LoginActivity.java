package com.quantum.proctorcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    //Widgets
    private EditText emailEt, passEt;
    private Button loginButton;
    private TextView forgotPwTv;
    private ProgressBar progressBar;

    //Instances
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String email, pass;
            email = emailEt.getText().toString();
            pass = passEt.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter the credentials to login", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEt.setError("Enter valid E-mail");
                Toast.makeText(LoginActivity.this, "Enter Valid E-mail", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                signIn(email, pass);
                loginButton.setEnabled(false);
            }

        });
    }

    private void signIn(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(task -> {
                    startActivity(new Intent(LoginActivity.this, LoadingScreen.class));
                    finish();
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                    loginButton.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Can't login", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                });
    }

    private void findViews() {
        emailEt = findViewById(R.id.email_login);
        passEt = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.login_btn);
        forgotPwTv = findViewById(R.id.forgot_login);
        progressBar = findViewById(R.id.progress_login);
    }

}