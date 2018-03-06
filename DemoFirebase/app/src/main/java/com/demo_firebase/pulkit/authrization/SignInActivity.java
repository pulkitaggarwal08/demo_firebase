package com.demo_firebase.pulkit.authrization;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo_firebase.pulkit.DashboardActivity;
import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.util.SnackbarUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class SignInActivity extends AppCompatActivity {

    private TextView tv_forgot_password, tv_register_user;

    private EditText et_email, et_password;
    private Button btn_login;

    private Intent intent;

    private String str_email, str_password;

    private FirebaseAuth firebaseAuth;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            intent = new Intent(SignInActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            init();
        }

    }

    private void init() {

        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
        tv_register_user = (TextView) findViewById(R.id.tv_register_user);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tv_register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignInActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_email = et_email.getText().toString();
                str_password = et_password.getText().toString();

                if (TextUtils.isEmpty(str_email)) {
                    Toast.makeText(SignInActivity.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(str_password)) {
                    Toast.makeText(SignInActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                signInUser(str_email, str_password);

            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signInUser(String str_email, String str_password) {

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            intent = new Intent(SignInActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // thrown if the password is not strong enough
                                Toast.makeText(SignInActivity.this, "Please enter the correct email and password!", Toast.LENGTH_LONG).show();
                            }
                            SnackbarUtil.showLongSnackbar(SignInActivity.this, "Login Unsuccessfull");

                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }


}
