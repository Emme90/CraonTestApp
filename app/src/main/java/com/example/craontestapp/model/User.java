package com.example.craontestapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "user_email")
    public String email;
    @ColumnInfo(name = "user_password")
    public String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}
}
