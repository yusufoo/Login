package com.redGuuner.login.View.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.redGuuner.login.R
import com.redGuuner.login.ViewModels.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var navigator: NavController
    private lateinit var viewModel: LoginViewModel
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

        setUpViewModel()

        configureFacebookSingIn()

        configureGoogleSignIn()

    }

    override
    fun onResume() {
        super.onResume()

        JoinNowTXT.setOnClickListener {
            requireActivity().onBackPressed()
        }

        ForgetPasswordTxt.setOnClickListener {
            toRestPasswordFragment()
        }

        SingInBTN.setOnClickListener {
            if (validateEmail() && validatePassword()) {
                viewModel.logIn(
                    SingInInputEmail.text.toString(),
                    SingInInputePassword.text.toString()
                )
            }
        }

        SingFacebookCircleImage.setOnClickListener {
            LoginFacebookLoginButton.performClick()
        }

        SingGoogleCircleImage.setOnClickListener {

            startGmailSingIn()
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
                    viewModel.loginWhitGoogle(account)

                }

            } catch (e: ApiException) {

            }

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }


    // validation
    private fun validateEmail(): Boolean {

        return if (SingInInputEmail.text!!.isEmpty()) {
            SingInEmailET.isErrorEnabled = true
            SingInEmailET.error = "Email Can't Be Empty"

            false

        } else {
            SingInEmailET.isErrorEnabled = false
            true
        }


    }

    private fun validatePassword(): Boolean {

        return when {
            SingInInputePassword.text!!.isEmpty() -> {

                SingInPasswordEt.isErrorEnabled = true
                SingInPasswordEt.error = "Password Can't Be Empty"
                false

            }
            SingInInputePassword.text!!.length < 6 -> {
                SingInPasswordEt.isErrorEnabled = true
                SingInPasswordEt.error = "Password Should Be At Least 6 Character"
                false


            }
            else -> {
                SingInPasswordEt.isErrorEnabled = false
                true
            }
        }


    }


    // ConFiguration
    private fun configureFacebookSingIn() {
        callbackManager = CallbackManager.Factory.create()
        LoginFacebookLoginButton.setPermissions(email)
        LoginFacebookLoginButton.fragment = this
        LoginFacebookLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    viewModel.loginWhitFacebook(loginResult!!.accessToken)
                }

                override fun onCancel() {
                    toast("Cancel")
                }

                override fun onError(exception: FacebookException) {

                    toast("We Found An Error : ${exception.message}")


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

    private fun startGmailSingIn() {
        val singInIntent = googleSinInClient.signInIntent
        startActivityForResult(singInIntent, googleSingInCode)

    }



    // utils
    private fun showLoginResult(result: String) {

        if (!result.isBlank()){
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
           viewModel.loginResult.value=""
        }

    }

    private fun toast(message :String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }


   // ViewModel And LiveData
    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun setUpObservers() {

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { response ->

            response?.let {result ->
                showLoginResult(result)
            }


        })

        viewModel.firebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                toShowInformationFragment()
                viewModel.setInformationToSP()
            }

        })
    }


   // Navigation
    private fun setUpNavigation(view: View) {
        navigator = Navigation.findNavController(view)

    }

    private fun toShowInformationFragment() {

        navigator.navigate(R.id.action_loginFragment_to_allInformation)

    }

    private fun toRestPasswordFragment() {
        navigator.navigate(R.id.action_loginFragment_to_restPasswordFragment)
    }


}
