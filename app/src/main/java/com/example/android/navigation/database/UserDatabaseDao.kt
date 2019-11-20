package com.example.android.navigation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(user: UserData)

    @Query("SELECT * FROM user_table WHERE username = :key")
    fun get(key: String): UserData

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("SELECT * FROM user_table ORDER BY username DESC LIMIT 1")
    fun getUser(): UserData?

    @Query("SELECT * FROM user_table ORDER BY gameScore DESC")
    fun getAllUserByScore(): LiveData<List<UserData>>?
}
