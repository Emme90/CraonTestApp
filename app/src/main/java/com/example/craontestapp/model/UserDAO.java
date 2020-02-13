package com.example.craontestapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("select * from users")
    List<User> getAllUser();

    @Query("select * from users where id=:id")
    User getUserById(int id);

    @Query("select * from users where email=:email")
    User getUserByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("delete from users where id=:id")
    void deleteUserById(int id);
}
