package com.example.craontestapp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    private UserDatabase() {
    }

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context,
                            UserDatabase.class,
                            "userDatabase")
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract UserDAO userDAO();
}
