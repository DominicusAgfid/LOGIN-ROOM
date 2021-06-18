package com.example.loginroom.auth.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.example.loginroom.MainActivity;
import com.example.loginroom.R;
import com.example.loginroom.auth.entity.DataUsers;
import com.example.loginroom.auth.entity.UsersAppDatabase;
import com.example.loginroom.auth.entity.UsersDAO;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp;
    private TextInputEditText etusername, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername = findViewById(R.id.ti_usernamelogin);
        etpassword = findViewById(R.id.ti_passwordlogin);

        btnSignIn = findViewById(R.id.btn_signin_loginview);
        btnSignUp = findViewById(R.id.btn_signup_loginview);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    UsersAppDatabase usersAppDatabase = UsersAppDatabase.inidb(getApplicationContext());
                    UsersDAO usersDAO = usersAppDatabase.usersDAO();

                    DataUsers dataUsers = usersDAO.login(username,password);
                    if (dataUsers == null){
                        Toast.makeText(getApplicationContext(), "Username and Password combination is invalid", Toast.LENGTH_SHORT).show();
                    }else{
                        SharedPreferences sharedPreferences = getSharedPreferences("SAVED_LOGIN", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("LOGGED",true);
                        editor.putString("USERNAME",username);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}