package com.redGuuner.login.ViewModels

import androidx.lifecycle.ViewModel
import com.redGuuner.login.Repostrys.FirebaseRepostry

class ReadInformationViewModel:ViewModel() {

    val userInformation by lazy {
        FirebaseRepostry.allUserInformation
    }

    fun getUserInformation() {
        FirebaseRepostry.getUserInformation()
    }

}