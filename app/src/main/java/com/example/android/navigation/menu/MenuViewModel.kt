package com.example.android.navigation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.navigation.Fruit
import timber.log.Timber

class MenuViewModel(): ViewModel() {
    var gameScore: Int
    var timeSpent: Int

    val playButtonVisible = Transformations.map(username) {
        Timber.i("changed")
        it != ""
    }

    private var _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    init {
        _username.value = ""
        gameScore = 0
        timeSpent = 0

    }

    fun setUsername(username: String) {
        _username.value = username
    }
}