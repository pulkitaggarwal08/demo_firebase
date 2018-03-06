package com.demo_firebase.pulkit.authrization;

import android.Manifest;
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

import com.demo_firebase.pulkit.R;
import com.demo_firebase.pulkit.models.UserInformation;
import com.demo_firebase.pulkit.util.SnackbarUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {

    private TextView tv_register_user;
    private EditText et_name, et_email, et_password, re_type_password;
    private Button btn_register;

    private Intent intent;

    private String str_name, str_email, str_password, str_retype_password;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        init();

    }

    private void init() {

        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        re_type_password = (EditText) findViewById(R.id.re_type_password);
        tv_register_user = (TextView) findViewById(R.id.tv_register_user);
        btn_register = (Button) findViewById(R.id.btn_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_name = et_name.getText().toString();
                str_email = et_email.getText().toString();
                str_password = et_password.getText().toString();

                if (TextUtils.isEmpty(str_name)) {
                    Toast.makeText(RegisterUserActivity.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(str_email)) {
                    Toast.makeText(RegisterUserActivity.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(str_password)) {
                    Toast.makeText(RegisterUserActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (str_password.length() <= 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser(str_email, str_password);
            }
        });
    }

    private void registerUser(String str_email, String str_password) {

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            //Save User Information
                            saveUserInfo(task.getResult().getUser());
                            Toast.makeText(RegisterUserActivity.this, "Registeration Successfully", Toast.LENGTH_SHORT).show();

                            intent = new Intent(RegisterUserActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            //display some message here
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // thrown if there already exists an account with the given email address
                                Toast.makeText(RegisterUserActivity.this, "This email is already exists", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // thrown if the email address is malformed (When not contains @)
                                Toast.makeText(RegisterUserActivity.this, "Email address contains invalid format, use special character like @,!", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                // thrown if the password is not strong enough
                                Toast.makeText(RegisterUserActivity.this, "Password is weak, enter a valid password", Toast.LENGTH_LONG).show();
                            }
                            SnackbarUtil.showLongSnackbar(RegisterUserActivity.this, "User Registeration Unsuccessfull");
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    private void saveUserInfo(FirebaseUser user) {

        String fullName = str_name;
        String email = user.getEmail();
        String userId = user.getUid();

//        UserInformation userInformation = new UserInformation(fullName, email);
//        databaseReference.child(userId).setValue(userInformation);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(userId, fullName);
        databaseReference.updateChildren(objectMap);

        DatabaseReference databaseReference2 = databaseReference.child(userId);
        DatabaseReference databaseReference3 = databaseReference2.child("user_info");

        Map<String, Object> objectMap3 = new HashMap<>();
        objectMap3.put("name", fullName);
        objectMap3.put("user_email", email);
        databaseReference3.updateChildren(objectMap3);

    }


}
