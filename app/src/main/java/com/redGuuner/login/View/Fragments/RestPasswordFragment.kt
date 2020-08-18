package com.redGuuner.login.View.Fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.redGuuner.login.R
import com.redGuuner.login.ViewModels.RestPasswordViewModel
import kotlinx.android.synthetic.main.fragment_rest_password.*

class RestPasswordFragment : Fragment(R.layout.fragment_rest_password) {


    private lateinit var viewModel: RestPasswordViewModel


   // System
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        SendLinkBTN.setOnClickListener {
            if (validateEmail()) {
                viewModel.resatPassword(RestPasswordInput.text.toString())
            }
        }
    }



    // Validation
    private fun validateEmail(): Boolean {

        return if (RestPasswordInput.text!!.isEmpty()) {
            RestPasswordET.isErrorEnabled = true
            RestPasswordET.error = "Email Can't Be Empty"

            false

        } else {
            RestPasswordET.isErrorEnabled = false
            true
        }


    }


   // utils
    private fun showRestPasswordResult(result: String)
    {

        if (!result.isBlank()){
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            viewModel.restPasswordResult.value=""
        }

    }



    //ViewModel And LiveData
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(RestPasswordViewModel::class.java)
    }

    private fun setupObservers(){
        viewModel.restPasswordResult.observe(viewLifecycleOwner, Observer { response ->
           response?.let {result ->
               showRestPasswordResult(result)
           }
        })
    }



}
