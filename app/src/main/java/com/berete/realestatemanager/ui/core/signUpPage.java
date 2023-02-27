package com.berete.realestatemanager.ui.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.berete.realestatemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUpPage extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            finish();
            return;
        }

        AppCompatButton btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        TextView toLogin = findViewById(R.id.to_login);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUpPage.this, loginPage.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(){
        EditText firstName = (EditText) findViewById(R.id.editFirstName);
        EditText lastName = (EditText) findViewById(R.id.editLastName);
        EditText emailID = (EditText)findViewById(R.id.signup_email);
        EditText passWord = (EditText)findViewById(R.id.signup_Password);

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String email = emailID.getText().toString();
        String password = passWord.getText().toString();

        if(fName.isEmpty() || lName.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please Fill All The Fields", Toast.LENGTH_LONG).show();
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Users user = new Users(fName, lName, email);
                            FirebaseDatabase.getInstance().getReference()
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(signUpPage.this, loginPage.class);
                                            startActivity(intent);
                                        }
                                    });
                        } else{
                            Toast.makeText(signUpPage.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
