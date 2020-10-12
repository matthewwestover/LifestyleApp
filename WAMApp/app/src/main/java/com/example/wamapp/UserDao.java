package com.example.wamapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Query("SELECT * from user_table LIMIT 1")//LIMIT 1
    User getUser();

    @Query("SELECT Count(*) from user_table")//LIMIT 1
    int getNumberOfUserInDatabase();

    @Query("DELETE from user_table")
    void deleteAll();
}
