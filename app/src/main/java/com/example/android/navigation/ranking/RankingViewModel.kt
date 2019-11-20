package com.example.android.navigation.ranking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.navigation.database.UserDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber

class RankingViewModel (val database: UserDatabaseDao,
                           application: Application
): AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var scoreList = database.getAllUserByScore()

    init {
        Timber.i("scoreList: ${scoreList}")
    }
}
