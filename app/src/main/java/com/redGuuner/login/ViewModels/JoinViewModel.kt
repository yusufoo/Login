package com.redGuuner.login.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.redGuuner.login.Repostrys.FirebaseRepostry
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class JoinViewModel(application: Application) : AndroidViewModel(application) {
    //constants
    private val userInfoKey = "UserInformationState"
    private val stateOfUserInformation = "state"
    private val context = getApplication<Application>().applicationContext

    //LiveData
    val joiningResult by lazy { FirebaseRepostry.joiningResult }

    val firebaseUser by lazy {
        FirebaseRepostry.currentUser
    }

    fun joinWhitEmail(
        email: String, password: String
    ) {
        FirebaseRepostry.join(email, password)
    }

    fun joinWhitFacebook(token: AccessToken) {

        FirebaseRepostry.joinWhitFacebook(token)
    }

    fun joinWhitGoogle(account: GoogleSignInAccount) {
        FirebaseRepostry.joinWhitGoogle(account)
    }

    fun checkIfUserAuthenticated(): Boolean {
        return FirebaseRepostry.authenticator.currentUser != null
    }

    fun checkIfUserHasInformation(): Boolean {
        return getUserInformationState()
    }

    private fun getUserInformationState(): Boolean {
        val sharedPreferences = context.getSharedPreferences(userInfoKey, 0)
        return sharedPreferences.getBoolean(stateOfUserInformation, false)
    }


}