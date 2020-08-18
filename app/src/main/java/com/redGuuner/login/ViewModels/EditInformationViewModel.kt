package com.redGuuner.login.ViewModels

import androidx.lifecycle.ViewModel
import com.redGuuner.login.Repostrys.FirebaseRepostry
import com.redGuuner.login.Utils.UserInformation

class EditInformationViewModel : ViewModel() {


   val upDatingUserInformation by lazy {
       FirebaseRepostry.upDatingUserInformation
   }

    fun editInformation(user: UserInformation) {
        FirebaseRepostry.updateUserInformation(user)
    }


}