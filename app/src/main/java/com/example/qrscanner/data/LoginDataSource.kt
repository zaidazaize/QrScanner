package com.example.qrscanner.data

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.qrscanner.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private  val mainActivity: FragmentActivity) {
    private val _result : MutableLiveData<Result<LoggedInUser>> = MutableLiveData()

   public val result : LiveData<Result<LoggedInUser>>
       get() {
           return _result
       }
    fun login(email: String, password: String,auth:FirebaseAuth) {
        var user : LoggedInUser = LoggedInUser("hey","hey")
        try {
            // TODO: handle loggedInUser authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mainActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                       user = LoggedInUser(auth.currentUser?.uid.toString(), auth.currentUser?.email.toString())
                        _result.value = Result.Success(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            mainActivity,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        } catch (e: Throwable) {
        }
    }

    fun logout(auth: FirebaseAuth) {
        // TODO: revoke authentication
        auth.signOut()

    }
    companion object{
        public  val TAG  = "Login details"
    }
}