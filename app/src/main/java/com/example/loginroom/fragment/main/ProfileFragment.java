package com.example.loginroom.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.example.loginroom.MainActivity;
import com.example.loginroom.R;
import com.example.loginroom.auth.entity.DataUsers;
import com.example.loginroom.auth.entity.UsersAppDatabase;
import com.example.loginroom.auth.entity.UsersDAO;
import com.example.loginroom.auth.view.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private TextView tvFullName,tvUsername, tvJoined;
    private Button btnUpdate, btnSignOut, btnDelete;
    private TextInputEditText etUsername, etPassword, etPasswordNew, etFullname;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences getPreferences = this.getActivity().getSharedPreferences("SAVED_LOGIN", Context.MODE_PRIVATE);
        String username = getPreferences.getString("USERNAME", "DEFAULT");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UsersAppDatabase usersAppDatabase = UsersAppDatabase.inidb(this.getContext());
        UsersDAO usersDAO = usersAppDatabase.usersDAO();
        DataUsers dataUsers = usersDAO.checkUser(username);

        etFullname = view.findViewById(R.id.ti_fullname_fragmentprofile);
        etFullname.setText(dataUsers.getName());

        etUsername = view.findViewById(R.id.ti_username_fragmentprofile);
        etUsername.setText(dataUsers.getUsername());

        etFullname = view.findViewById(R.id.ti_fullname_fragmentprofile);
        etUsername = view.findViewById(R.id.ti_username_fragmentprofile);
        etPassword = view.findViewById(R.id.ti_password_fragmentprofile);
        etPasswordNew = view.findViewById(R.id.ti_passwordnew_fragmentprofile);

        btnUpdate = view.findViewById(R.id.btn_update_fragmentprofile);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etFullname.getText().toString().isEmpty() && !etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() && !etPasswordNew.getText().toString().isEmpty()) {
                    DataUsers checkUsernameAvailability = usersDAO.checkUser(etUsername.getText().toString());
                    if (!dataUsers.getPassword().equals(etPassword.getText().toString())) {
                        Toast.makeText(v.getContext(), "Wrong Old Password", Toast.LENGTH_SHORT).show();
                    }else{
                        if (dataUsers.getUsername().equals(etUsername.getText().toString())){
                            DataUsers newDataUser = new DataUsers();
                            newDataUser.setId(dataUsers.getId());
                            newDataUser.setName(etFullname.getText().toString());
                            newDataUser.setUsername(dataUsers.getUsername());
                            newDataUser.setPassword(etPasswordNew.getText().toString());
                            newDataUser.setCreatedAt(dataUsers.getCreatedAt());

                            usersDAO.updateData(newDataUser);

                            ((MainActivity)getActivity()).reload();
                        }else{
                            if (checkUsernameAvailability!=null){
                                Toast.makeText(v.getContext(), "Username Has Been Registered", Toast.LENGTH_SHORT).show();
                            }else{
                                DataUsers newDataUser = new DataUsers();
                                newDataUser.setId(dataUsers.getId());
                                newDataUser.setName(etFullname.getText().toString());
                                newDataUser.setUsername(etUsername.getText().toString());
                                newDataUser.setPassword(etPasswordNew.getText().toString());
                                newDataUser.setCreatedAt(dataUsers.getCreatedAt());

                                usersDAO.updateData(newDataUser);

                                SharedPreferences.Editor editor = getPreferences.edit();
                                editor.putString("USERNAME",etUsername.getText().toString());
                                editor.apply();

                                ((MainActivity)getActivity()).reload();
                            }
                        }
                    }
                }else{
                    Toast.makeText(v.getContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete = view.findViewById(R.id.btn_delete_fragmentprofile);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersDAO.deleteSingleData(username);
                SharedPreferences.Editor editor = getPreferences.edit();
                editor.putBoolean("LOGGED",false);
                editor.putString("USERNAME","DEFAULT");
                editor.apply();

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSignOut = view.findViewById(R.id.btn_signout_fragmentprofile);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getPreferences.edit();
                editor.putBoolean("LOGGED",false);
                editor.putString("USERNAME","DEFAULT");
                editor.apply();

                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private Boolean validation(DataUsers dataUsers){
        if (!dataUsers.getName().isEmpty() && !dataUsers.getUsername().isEmpty() && !dataUsers.getPassword().isEmpty()){
            UsersAppDatabase usersAppDatabase = UsersAppDatabase.inidb(this.getContext());
            UsersDAO usersDAO = usersAppDatabase.usersDAO();

            DataUsers validateUsername = usersDAO.checkUser(dataUsers.getUsername());
            if (validateUsername != null){
                Toast.makeText(this.getContext(), "Username has been registered", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        }else{
            Toast.makeText(this.getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}