package com.demo_firebase.pulkit.authrization;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo_firebase.pulkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private String str_email;
    private EditText et_email;
    private Button btn_forgot_password;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        init();
    }

    private void init() {

        et_email = (EditText) findViewById(R.id.et_email);
        btn_forgot_password = (Button) findViewById(R.id.btn_forgot_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_email = et_email.getText().toString().trim();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_email.getWindowToken(), 0);

                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(ForgotPasswordActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                forgotPasswordEmail(str_email);
            }
        });
    }

    private void forgotPasswordEmail(String str_email) {

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(str_email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

}
