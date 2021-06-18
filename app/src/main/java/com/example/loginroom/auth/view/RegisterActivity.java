package com.example.loginroom.auth.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.example.loginroom.R;
import com.example.loginroom.auth.entity.DataUsers;
import com.example.loginroom.auth.entity.UsersAppDatabase;
import com.example.loginroom.auth.entity.UsersDAO;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp;
    private TextInputEditText etname, etusername, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignIn = findViewById(R.id.btn_signin_registerview);
        btnSignUp = findViewById(R.id.btn_signup_registerview);

        etname = findViewById(R.id.ti_name);
        etusername = findViewById(R.id.ti_username);
        etpassword = findViewById(R.id.ti_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataUsers dataUsers = new DataUsers();
                dataUsers.setName(etname.getText().toString());
                dataUsers.setUsername(etusername.getText().toString());
                dataUsers.setPassword(etpassword.getText().toString());

                if (validation(dataUsers)){
//                    Query Insert
                    UsersAppDatabase usersAppDatabase = UsersAppDatabase.inidb(getApplicationContext());
                    UsersDAO usersDAO = usersAppDatabase.usersDAO();
                    usersDAO.insertData(dataUsers);

                    Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                    RegisterActivity.this.startActivity(intent);
                    finish();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validation(DataUsers dataUsers){
        if (!dataUsers.getName().isEmpty() && !dataUsers.getUsername().isEmpty() && !dataUsers.getPassword().isEmpty()){
            UsersAppDatabase usersAppDatabase = UsersAppDatabase.inidb(getApplicationContext());
            UsersDAO usersDAO = usersAppDatabase.usersDAO();

            DataUsers validateUsername = usersDAO.checkUser(dataUsers.getUsername());
            if (validateUsername != null){
                Toast.makeText(getApplicationContext(), "Username has been registered", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        }else{
            Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}