package com.example.android.navigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "user_table")
data class UserData (
        @PrimaryKey(autoGenerate = true)
        var userId: Int = 0,
        @ColumnInfo(name="username")
        var username: String = "",
        @ColumnInfo(name="gameScore")
        var gameScore: Int = 0,
        @ColumnInfo(name="timeSpent")
        var timeSpent: Int = 0
)