package com.example.qrscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //for firebase auth
    private lateinit var auth: FirebaseAuth

    private lateinit var authStateListener: AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    override fun onResume() {
        super.onResume()
        auth.addAuthStateListener (getAuthStateListener())
    }

    fun getAuthStateListener():  AuthStateListener {
        authStateListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                Navigation.findNavController(this@MainActivity, R.id.nav_host)
                    .navigate(R.id.action_global_loginFragment)
            } else {
                // User is signed in

            }
        }
        return authStateListener
    }

    override fun onPause() {
        super.onPause()
        auth.removeAuthStateListener(authStateListener)
    }
}