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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText mname,memail,mpassword;
    private Button mregisterbtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private Toolbar mtoolbar;
    private ProgressDialog mprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mprogress = new ProgressDialog(this);

        mname = (EditText) findViewById(R.id.nametxt);
        memail = (EditText) findViewById(R.id.emailtxt);
        mpassword = (EditText) findViewById(R.id.passwordtxt);
        mregisterbtn = (Button) findViewById(R.id.registerbtn);

        mtoolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mname.getText().toString().trim();
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) )
                {
                    register(name,email,password);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Please Enter Details",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void register(final String name, String email, String password) {

        mprogress.setTitle("Signing Up");
        mprogress.setMessage("creating account please wait...");
        mprogress.show();
        mprogress.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String uid = mAuth.getCurrentUser().getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String, String> userMap = new HashMap<String, String>();
                    userMap.put("name",name);
                    userMap.put("status","Hi there,I'm using chatter");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                mprogress.dismiss();
                                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,"Something went wrong in saving user data in database...",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    mprogress.dismiss();
                    Toast.makeText(RegisterActivity.this,"Something went wrong...",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
