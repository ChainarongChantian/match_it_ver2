package com.example.android.navigation

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.navigation.database.UserData
import com.example.android.navigation.database.UserDatabase
import com.example.android.navigation.database.UserDatabaseDao
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {

    private lateinit var userDao: UserDatabaseDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        userDao = db.userDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val user = UserData(username = "aloha")
        userDao.insert(user)
        val currentUser = userDao.getUser()
        assertEquals(currentUser?.username, "aloha")
    }
}
