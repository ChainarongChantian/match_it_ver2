package com.example.android.navigation.game_done

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.navigation.R
import com.example.android.navigation.database.UserData
import com.example.android.navigation.database.UserDatabaseDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import timber.log.Timber

class GameDoneViewModel(var username: String,
                        var gameScore: Int,
                        var timeSpent: Int,
                        val database: UserDatabaseDao,
                        application: Application
                        ): AndroidViewModel(application) {

    var gameScoreText = gameScore.toString()
    var timeSpentText = timeSpent.toString()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Timber.i("GameViewModel created!!")
        uiScope.launch {
            storeScore(username, gameScore, timeSpent)
        }
    }

    private suspend fun storeScore(username: String, score: Int, time: Int) {
        return withContext(Dispatchers.IO) {
            val newUser = UserData(username = username, gameScore = score, timeSpent = time)
            database.insert(newUser)
//            database.clear()
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        Timber.i("GameViewModel destroyed!!")
//    }
}