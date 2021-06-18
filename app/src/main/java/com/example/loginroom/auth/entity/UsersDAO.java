package com.example.loginroom.auth.entity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDAO {
    @Insert
    Long insertData(DataUsers dataUsers);

    @Query("SELECT * FROM users_db")
    List<DataUsers> getAllData();

    @Query("SELECT * FROM users_db WHERE id = :id")
    DataUsers selectUser(int id);

    @Query("SELECT * FROM users_db WHERE username = :username")
    DataUsers checkUser(String username);

    @Query("SELECT * FROM users_db WHERE username = :username and password = :password")
    DataUsers login(String username, String password);

    @Update
    int updateData(DataUsers user);

    @Query("DELETE FROM users_db WHERE username= :username")
    void deleteSingleData(String username);
}
