package com.redGuuner.login.ViewModels

import androidx.lifecycle.ViewModel
import com.redGuuner.login.Repostrys.FirebaseRepostry

class RestPasswordViewModel : ViewModel() {


    val restPasswordResult by lazy {
        FirebaseRepostry.restPasswordResult
    }

    fun resatPassword(email: String) {
        FirebaseRepostry.resatPassword(email)
    }

}