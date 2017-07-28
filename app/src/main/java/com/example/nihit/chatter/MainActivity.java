package com.example.nihit.chatter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;

    private ViewPager mviewpager;
    private SectionsPagerAdapter msectionpageradapter;
    private TabLayout mtablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mviewpager = (ViewPager) findViewById(R.id.tabPager);
        msectionpageradapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mviewpager.setAdapter(msectionpageradapter);

        mtablayout = (TabLayout) findViewById(R.id.main_tabs);
        mtablayout.setupWithViewPager(mviewpager);

        mAuth = FirebaseAuth.getInstance();
        mtoolbar = (Toolbar) findViewById(R.id.mainpage_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Chatter");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser==null)
        {

            sendtostart();
        }
    }

    private void sendtostart() {
        Intent i = new Intent(MainActivity.this,StartActivity.class);
        startActivity(i);
        finish();//user cannot press back button

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.logoutbtn)
        {
            FirebaseAuth.getInstance().signOut();
           sendtostart();
        }

        else if(item.getItemId()==R.id.settingsbtn)
        {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
        }

        return true;
    }
}
