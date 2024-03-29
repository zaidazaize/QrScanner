package com.example.qrscanner.data

import androidx.lifecycle.LiveData
import com.example.qrscanner.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout(auth: FirebaseAuth) {
        user = null
        dataSource.logout(auth)
    }

    fun login(username: String, password: String, auth: FirebaseAuth): LiveData<Result<LoggedInUser>> {
        // handle login
        dataSource.login(username, password,auth)
        return dataSource.result
//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
//
//        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}