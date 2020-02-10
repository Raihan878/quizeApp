package com.uylab.quiz.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uylab.quiz.R;
import com.uylab.quiz.databinding.ActivityLogInBinding;
import com.uylab.quiz.main.MainActivity;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding logInBinding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInBinding = DataBindingUtil.setContentView(this,R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        logInBinding.loginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = logInBinding.userID.getText().toString().trim();
                String password = logInBinding.passwordID.getText().toString().trim();
                login(userName,password);
            }
        });
        logInBinding.registationID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = logInBinding.userID.getText().toString().trim();
                String password = logInBinding.passwordID.getText().toString().trim();
                registation(userName,password);
            }
        });
    }
    public void login(String userName,String password){
        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(LogInActivity.this,"Success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LogInActivity.this,"Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    public void registation(String userName,String password){
        mAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogInActivity.this,"Success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LogInActivity.this,"Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
