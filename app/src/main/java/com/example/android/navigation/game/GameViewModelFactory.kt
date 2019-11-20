package com.example.android.navigation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(

        private val username: String,
        private val gameScore: Int,
        private val timeSpent: Int
        ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(username, gameScore, timeSpent) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}