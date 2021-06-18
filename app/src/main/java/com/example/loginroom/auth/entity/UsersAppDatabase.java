package com.example.loginroom.auth.entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DataUsers.class}, version = 1)
public abstract class UsersAppDatabase extends RoomDatabase {
    public abstract UsersDAO usersDAO();
    private static UsersAppDatabase usersAppDatabase;

    public static UsersAppDatabase inidb(Context context){
        if (usersAppDatabase == null){
            usersAppDatabase = Room.databaseBuilder(context, UsersAppDatabase.class,"dbusers")
                    .allowMainThreadQueries()
                    .build();
        }
        return usersAppDatabase;
    }

}
