package com.redGuuner.login.View.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.redGuuner.login.R
import com.redGuuner.login.Utils.UserInformation
import com.redGuuner.login.ViewModels.InsertInformationViewModel
import kotlinx.android.synthetic.main.fragment_insert_information.*


class InsertInformationFragment : Fragment(R.layout.fragment_insert_information) {

    private lateinit var navigator: NavController
    private lateinit var viewModel: InsertInformationViewModel

    //System
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
    fun onResume() {
        super.onResume()

        SaveinformationBTN.setOnClickListener {
            if (validateAllComponent()) {
                viewModel.saveUserInformation(getUserInformation())
            }
        }
    }


   //Validation
    private fun validateAllComponent(): Boolean {
        return validateFullName() && validateEmail() && validateCountry() && validateCity() && validateAddressOne() && validateZip()

    }

    private fun validateFullName(): Boolean {
        return if (FullNameInput.text!!.isBlank()) {
            FullNameET.isErrorEnabled = true
            FullNameET.error = "Full Name Can't Be Empty"
            false

        } else {
            FullNameET.isErrorEnabled = false
            true
        }
    }

    private fun validateEmail(): Boolean {

        return if (EmailInput.text!!.isBlank()) {
            EmailET.isErrorEnabled = true
            EmailET.error = "Email Can't Be Empty"
            false
        } else {
            EmailET.isErrorEnabled = false
            true
        }

    }

    private fun validateCountry(): Boolean {
        return if (CountryInput.text!!.isBlank()) {
            CountryET.isErrorEnabled = true
            CountryET.error = "Country Can't Be Empty"
            false
        } else {
            CountryET.isErrorEnabled = false
            true
        }

    }

    private fun validateCity(): Boolean {
        return if (CityInput.text!!.isBlank()) {
            CityET.isErrorEnabled = true
            CityET.error = "City Can't Be Empty"
            false
        } else {
            CityET.isErrorEnabled = false
            true
        }

    }

    private fun validateAddressOne(): Boolean {
        return if (AddressOneInput.text!!.isBlank()) {
            AddressOneET.isErrorEnabled = true
            AddressOneET.error = "Address 1 Can't Be Empty"
            false
        } else {
            AddressOneET.isErrorEnabled = false
            true
        }

    }

    private fun validateZip(): Boolean {
        return if (ZipInput.text!!.isBlank()) {
            ZipET.isErrorEnabled = true
            ZipET.error = "Zip Can't Be Empty"
            false
        } else {
            ZipET.isErrorEnabled = false
            true
        }

    }

    // ViewModel And LiveData
    private fun setUpViewModel() {

        viewModel = ViewModelProvider(this).get(InsertInformationViewModel::class.java)

    }

    private fun setUpObservers() {

        viewModel.confirmation.observe(viewLifecycleOwner, Observer { confirmation ->

            if (confirmation) {
                viewModel.setInformationToSP()
                toAllInformationFragment()}
        })

        viewModel.savingInformationResult.observe(viewLifecycleOwner, Observer { response ->

            response?.let {result ->
                showSavingResult(result)
            }
        })


    }


    // Navigation
    private fun setUpNavigation(view: View) {
        navigator = Navigation.findNavController(view)

    }

    private fun toAllInformationFragment() {
        navigator.navigate(R.id.action_insertInformationFragment_to_allInformation)

    }



   // utils
    private fun showSavingResult(result:String){
        if (!result.isBlank()){
            Toast.makeText(context,result,Toast.LENGTH_LONG).show()
            viewModel.savingInformationResult.value=""
        }

    }


    private fun getUserInformation(): UserInformation {
        if (AddressTowInput.text!!.isBlank()) {
            return UserInformation(
                FullName = FullNameInput.text.toString(),
                Email = EmailInput.text.toString(),
                Country = CountryInput.text.toString(),
                City = CityInput.text.toString(),
                AddressOne = AddressOneInput.text.toString(),
                Zip = ZipInput.text.toString()
            )
        } else {
            return UserInformation(
                FullName = FullNameInput.text.toString(),
                Email = EmailInput.text.toString(),
                Country = CountryInput.text.toString(),
                City = CityInput.text.toString(),
                AddressOne = AddressOneInput.text.toString(),
                AddressTow = AddressTowInput.text.toString(),
                Zip = ZipInput.text.toString()
            )
        }

    }



}
