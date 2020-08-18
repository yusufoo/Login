package com.redGuuner.login.Repostrys

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.redGuuner.login.Utils.UserInformation
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepostry {

    //FireBase
    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private const val collectionsPath = "Users Information's"
    val authenticator = FirebaseAuth.getInstance()

    //LiveData
    val currentUser = MutableLiveData<FirebaseUser>()
    val joiningResult=MutableLiveData<String>()
    val loginResult=MutableLiveData<String>()
    val restPasswordResult=MutableLiveData<String>()
    val savingInformationResult=MutableLiveData<String>()
    val upDatingUserInformation=MutableLiveData<String>()
    val confirmation = MutableLiveData<Boolean>()
    val allUserInformation = MutableLiveData<String>()

 //joining Logic
    fun join(
        email: String,
        password: String
    ) {

     authenticator.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    joiningResult.value = "Welcome"
                    currentUser.value = authenticator.currentUser
                    Log.i("AuthentctionProcec","Login Succes")


                }

            }.addOnFailureListener { exception ->
             Log.i("AuthentctionProcec","${exception.message}")

                joiningResult.value = exception.message


            }


    }

    fun joinWhitFacebook(
        token: AccessToken
    ) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        authenticator.signInWithCredential(credential)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    joiningResult.value = "Welcome"
                    currentUser.value = authenticator.currentUser
                }

            }
            .addOnFailureListener { exception ->

                joiningResult.value = exception.message

            }

    }

    fun joinWhitGoogle(
        account: GoogleSignInAccount
    ) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        authenticator.signInWithCredential(credential)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    joiningResult.value = "Welcome"
                    currentUser.value = authenticator.currentUser
                }

            }
            .addOnFailureListener { exception ->
                joiningResult.value = exception.message

            }

    }

    //Login Logic
    fun logIn(
        email: String,
        password: String
    ) {
        authenticator.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    loginResult.value = "Welcome Back"
                    currentUser.value = authenticator.currentUser
                }

            }
            .addOnFailureListener { exception ->
                loginResult.value = exception.message

            }
    }

    fun loginWhitFacebook(
        token: AccessToken
    ) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        authenticator.signInWithCredential(credential)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    loginResult.value = "Welcome"
                    currentUser.value = authenticator.currentUser
                }

            }
            .addOnFailureListener { exception ->

                loginResult.value = exception.message

            }

    }

    fun loginWhitGoogle(
        account: GoogleSignInAccount
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        authenticator.signInWithCredential(credential)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    loginResult.value = "Welcome"
                    currentUser.value = authenticator.currentUser
                }

            }
            .addOnFailureListener { exception ->

                loginResult.value = exception.message

            }

    }

    fun resatPassword(email: String) {
        authenticator
            .sendPasswordResetEmail(email)
            .addOnCompleteListener { resulte ->
                if (resulte.isSuccessful) {
                    restPasswordResult.value = "We Sent You a Link Please Check Your Email"
                } else if (resulte.isCanceled) {
                    restPasswordResult.value = "Canceled"

                }

            }
            .addOnFailureListener { exception ->
                restPasswordResult.value = exception.message

            }
    }

    fun saveUserInformation(user: UserInformation) {
        firebaseDatabase
            .collection(collectionsPath)
            .document(authenticator.currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                savingInformationResult.value = "Saved"
                allUserInformation.value = user.printAllInformation()
                confirmation.value = true


            }.addOnFailureListener { exception ->
                savingInformationResult.value = exception.message
                confirmation.value = false

            }
    }

    fun updateUserInformation(user: UserInformation){

        firebaseDatabase
            .collection(collectionsPath)
            .document(authenticator.currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                upDatingUserInformation.value = "Saved"
                allUserInformation.value = user.printAllInformation()


            }.addOnFailureListener { exception ->
                upDatingUserInformation.value = exception.message

            }
    }


    fun getUserInformation() {

        firebaseDatabase
            .collection(collectionsPath)
            .document(authenticator.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->

                if (documentSnapshot.exists()) {
                    val userInformation = documentSnapshot.toObject(UserInformation::class.java)
                    allUserInformation.value = userInformation?.printAllInformation()
                }


            }.addOnFailureListener { exception ->

                allUserInformation.value = exception.message
            }

    }


}