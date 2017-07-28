package com.example.nihit.chatter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText memial,mpassword;
    private Button mloginbtn;

    private Toolbar mtoolbar;
    private ProgressDialog mprogress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mprogress = new ProgressDialog(this);
        mtoolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        memial = (EditText) findViewById(R.id.email);
        mpassword  = (EditText) findViewById(R.id.password);
        mloginbtn = (Button) findViewById(R.id.loginbtn);

        mtoolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = memial.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                {
                    login(email,password);
                }
            }
        });

    }

    private void login(String email, String password) {

        mprogress.setTitle("Logging In");
        mprogress.setMessage("Please wait while you are logged in...");
        mprogress.setCanceledOnTouchOutside(false);
        mprogress.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    mprogress.dismiss();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
                else
                {
                    mprogress.dismiss();
                    Toast.makeText(LoginActivity.this,"Failed to Login..",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
