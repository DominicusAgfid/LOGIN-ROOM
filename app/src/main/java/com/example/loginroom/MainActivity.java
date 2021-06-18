package com.example.loginroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.loginroom.auth.view.LoginActivity;

import androidx.fragment.app.Fragment;

import com.example.loginroom.fragment.main.GithubFragment;
import com.example.loginroom.fragment.main.ProfileFragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences getPreferences = getSharedPreferences("SAVED_LOGIN", MODE_PRIVATE);
        Boolean check = getPreferences.getBoolean("LOGGED",false);
        String username = getPreferences.getString("USERNAME", "DEFAULT");

        if (!check){
            Intent intent = new Intent(MainActivity.this , LoginActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bot_navbar_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new Fragment());

        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.main_frame,new GithubFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.ic_github_menu:
                fragment = new GithubFragment();
                break;

            case R.id.ic_user_menu:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    public void reload(){
        getSupportFragmentManager().
                beginTransaction().replace(R.id.main_frame,new ProfileFragment()).commit();
    }
}