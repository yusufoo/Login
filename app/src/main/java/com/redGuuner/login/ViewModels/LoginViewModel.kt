package com.redGuuner.login.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.redGuuner.login.Repostrys.FirebaseRepostry
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val userInfoKey = "UserInformationState"
    private val stateOfUserInformation = "state"


    val loginResult by lazy {
        FirebaseRepostry.loginResult
    }
    val firebaseUser by lazy {
        FirebaseRepostry.currentUser
    }

    fun logIn(email: String, password: String) {
        FirebaseRepostry.logIn(email, password)
    }

    fun loginWhitFacebook(token: AccessToken) {

        FirebaseRepostry.loginWhitFacebook(token)
    }


    fun loginWhitGoogle(account: GoogleSignInAccount) {
        FirebaseRepostry.loginWhitGoogle(account)
    }

    fun setInformationToSP() {
        val sharedPreferences = context.getSharedPreferences(userInfoKey, 0)
        sharedPreferences.edit().putBoolean(stateOfUserInformation, true).apply()
    }


}