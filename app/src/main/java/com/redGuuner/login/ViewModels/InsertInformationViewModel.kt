package com.redGuuner.login.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.redGuuner.login.Repostrys.FirebaseRepostry
import com.redGuuner.login.Utils.UserInformation

class InsertInformationViewModel(application: Application) : AndroidViewModel(application) {

    private val userInfoKey = "UserInformationState"
    private val stateOfUserInformation = "state"
    private val context = getApplication<Application>().applicationContext


    val savingInformationResult by lazy {
        FirebaseRepostry.savingInformationResult
    }

    val confirmation by lazy {
        FirebaseRepostry.confirmation
    }

    fun saveUserInformation(user: UserInformation) {
        FirebaseRepostry.saveUserInformation(user)
    }

    fun setInformationToSP() {
        val sharedPreferences = context.getSharedPreferences(userInfoKey, 0)
        sharedPreferences.edit().putBoolean(stateOfUserInformation, true).apply()
    }







}