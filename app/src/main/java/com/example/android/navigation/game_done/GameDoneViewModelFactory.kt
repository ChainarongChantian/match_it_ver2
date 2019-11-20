package com.example.android.navigation.game_done

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.database.UserDatabaseDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GameDoneViewModelFactory(

        private val username: String,
        private val gameScore: Int,
        private val timeSpent: Int,
        private val dataSource: UserDatabaseDao,
        private val application: Application

        ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameDoneViewModel::class.java)) {
            return GameDoneViewModel(
                    username, gameScore, timeSpent, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}