package com.example.android.navigation.ranking

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.database.UserDatabaseDao

class RankingViewModelFactory (
        private val dataSource: UserDatabaseDao,
        private val application: Application
)
    : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankingViewModel::class.java)) {
            return RankingViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

