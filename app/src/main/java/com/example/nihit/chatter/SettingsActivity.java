package com.example.nihit.chatter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private TextView mdisplayname,mdisplaystatus;
    private Button mchangeimg,mchangestatus;
    private CircleImageView mImageView;

    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


        mImageView = (CircleImageView) findViewById(R.id.image);
        mdisplayname = (TextView) findViewById(R.id.displayname);
        mdisplaystatus = (TextView) findViewById(R.id.displaystatus);
        mchangeimg = (Button) findViewById(R.id.changeimg);
        mchangestatus = (Button) findViewById(R.id.changestatus);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String thumb = dataSnapshot.child("thumb_image").getValue().toString();

                mdisplayname.setText(name);
                mdisplaystatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
