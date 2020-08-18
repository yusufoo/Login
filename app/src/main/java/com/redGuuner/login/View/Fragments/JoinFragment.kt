package com.redGuuner.login.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.redGuuner.login.R
import com.redGuuner.login.ViewModels.JoinViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fragment_join.*


class JoinFragment : Fragment(R.layout.fragment_join) {

    private lateinit var navigator: NavController
    private lateinit var viewModel: JoinViewModel

    private val email = "email"
    private val googleSingInCode = 1
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSinInClient: GoogleSignInClient


    // System
    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNavigation(view)
    }

    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewModel()
        setUpObservers()
    }

    override
    fun onStart() {
        super.onStart()

        checkIfUserAuthenticated()

        configureGoogleSignIn()

        registerFacebookCallBack()
    }

    override
    fun onResume() {
        super.onResume()

        SingInText.setOnClickListener {
            toLoginFragment()
        }

        JoinNowBtn.setOnClickListener {
            if (validateEmail() && validatePassword()) {

                viewModel.joinWhitEmail(
                    JoinInputEmail.text.toString(),
                    JoinInputPassword.text.toString()
                )
            }
        }

        GoogleCircleImage.setOnClickListener {
            showGmailDialog()
        }

        FacebookCircleImage.setOnClickListener {
            JoinFacebookLoginButton.performClick()
        }
    }

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleSingInCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)

                account?.let {
                    viewModel.joinWhitGoogle(account)

                }

            } catch (e: ApiException) {

            }


        } else {


            callbackManager.onActivityResult(requestCode, resultCode, data)
        }

    }


    // Validation
    private fun validateEmail(): Boolean {

        return if (JoinInputEmail.text!!.isEmpty()) {
            JoinEmailET.isErrorEnabled = true
            JoinEmailET.error = "Email Can't Be Empty"

            false

        } else {
            JoinEmailET.isErrorEnabled = false
            true
        }


    }

    private fun validatePassword(): Boolean {

        return when {
            JoinInputPassword.text!!.isEmpty() -> {

                JoinPasswordEt.isErrorEnabled=true
                JoinPasswordEt.error="Password Can't Be Empty"
                false

            }
            JoinInputPassword.text!!.length< 6 -> {
                JoinPasswordEt.isErrorEnabled=true
                JoinPasswordEt.error="Password Should Be At Least 6 Character"
                false



            }
            else -> {
                JoinPasswordEt.isErrorEnabled=false
                true
            }
        }


    }

    private fun checkIfUserAuthenticated() {
        Log.i("User verification", "Started")
        if (viewModel.checkIfUserAuthenticated()) {
            Log.i("User verification", "User is Authenticated")
            Log.i("User verification", "Start Looking For His Information")
            if (viewModel.checkIfUserHasInformation()) {
                Log.i(
                    "User verification",
                    "User Has Put His information Now Send Him To All Information Fragment"
                )

                toAllInformationFragment()

            } else {
                Log.i(
                    "User verification",
                    "User Dos Not Have Put His Information Now Send Him aging To Put His Information"
                )
                toInsertInfoFragment()
            }

        } else {
            Log.i("User verification", "User Is Not Authenticated Staying On Login Page")
        }
    }


   // ConFiguration
    private fun registerFacebookCallBack() {
        callbackManager = CallbackManager.Factory.create();
        JoinFacebookLoginButton.setPermissions(email)
        JoinFacebookLoginButton.fragment = this
        JoinFacebookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    viewModel.joinWhitFacebook(loginResult!!.accessToken)
                }

                override fun onCancel() {
                    toast("Cancel")
                }

                override fun onError(exception: FacebookException) {
                    exception.message?.let { exceptionMsg ->
                        toast(exceptionMsg)

                    }

                }

            })


    }

    private fun configureGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSinInClient = GoogleSignIn.getClient(requireView().context, googleSignInOptions)
    }

    private fun showGmailDialog() {
        val singInIntent = googleSinInClient.signInIntent
        startActivityForResult(singInIntent, googleSingInCode)

    }


    // Utils
    private fun showJoiningResult(toastMsg: String) {
        if (!toastMsg.isBlank()){
            Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show()
            viewModel.joiningResult.value=""

        }
    }

    private fun toast(message :String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }


    // ViewModel And LiveData
    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(JoinViewModel::class.java)
    }

    private fun setUpObservers() {

        viewModel.joiningResult.observe(viewLifecycleOwner, Observer {Result ->
            Result?.let {result ->
                showJoiningResult(result)
            }
        })

        viewModel.firebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            firebaseUser?.let {
                toInsertInfoFragment()
            }
        })


    }



    // Navigation
    private fun setUpNavigation(view: View) {
        navigator = Navigation.findNavController(view)

    }

    private fun toLoginFragment() {
        navigator.navigate(R.id.action_joinFragment2_to_loginFragment)
    }

    private fun toInsertInfoFragment() {
        navigator.navigate(R.id.action_joinFragment2_to_insertInformationFragment)

    }

    private fun toAllInformationFragment() {
        navigator.navigate(R.id.action_joinFragment2_to_allInformation)
    }

}



