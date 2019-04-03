package com.obenacademy.myhealth;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private Button buttonRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        textViewSignin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    private void registerUser(){
    String email = editTextEmail.getText().toString().trim();
    String password = editTextPassword.getText().toString().trim();

    if(TextUtils.isEmpty(email)){
        // email is empty
        Toast.makeText(this, "Please email is required", Toast.LENGTH_SHORT).show();
        return;
    }
    if(TextUtils.isEmpty(password)){
        Toast.makeText(this, "Please password is needed", Toast.LENGTH_SHORT).show();
        return;
    }
    progressDialog.setMessage("Registering User, please wait");
    progressDialog.show();

    firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(LoginActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
}

    @Override
    public void onClick(View view) {
      if(view == textViewSignin) {
          //will open login activity here
      }
        if(view == buttonRegister) {
            registerUser();
        }
    }
}
