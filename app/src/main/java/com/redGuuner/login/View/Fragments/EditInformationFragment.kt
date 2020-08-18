package com.redGuuner.login.View.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.redGuuner.login.R
import com.redGuuner.login.Utils.UserInformation
import com.redGuuner.login.ViewModels.EditInformationViewModel
import kotlinx.android.synthetic.main.fragment_edit_information.*
import kotlinx.android.synthetic.main.fragment_edit_information.AddressOneET
import kotlinx.android.synthetic.main.fragment_edit_information.AddressOneInput
import kotlinx.android.synthetic.main.fragment_edit_information.AddressTowInput
import kotlinx.android.synthetic.main.fragment_edit_information.CityET
import kotlinx.android.synthetic.main.fragment_edit_information.CityInput
import kotlinx.android.synthetic.main.fragment_edit_information.CountryET
import kotlinx.android.synthetic.main.fragment_edit_information.CountryInput
import kotlinx.android.synthetic.main.fragment_edit_information.EmailET
import kotlinx.android.synthetic.main.fragment_edit_information.EmailInput
import kotlinx.android.synthetic.main.fragment_edit_information.FullNameET
import kotlinx.android.synthetic.main.fragment_edit_information.FullNameInput
import kotlinx.android.synthetic.main.fragment_edit_information.ZipET
import kotlinx.android.synthetic.main.fragment_edit_information.ZipInput

class EditInformationFragment : Fragment(R.layout.fragment_edit_information) {


    private lateinit var navigator: NavController
    private lateinit var viewModel: EditInformationViewModel



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


    override fun onResume() {
        super.onResume()
        EditinformationBTN.setOnClickListener {
            if (validateAllComponent()) {
                viewModel.editInformation(getUserInformation())
            }
        }
    }


    // Navigation
    private fun setUpNavigation(view: View) {
        navigator = Navigation.findNavController(view)

    }

    // ViewModel And LiveData
    private fun setUpViewModel() {

        viewModel = ViewModelProvider(this).get(EditInformationViewModel::class.java)

    }

    private fun setUpObservers() {

        viewModel.upDatingUserInformation.observe(viewLifecycleOwner, Observer {response ->

            response?.let {result ->
                showUpdatingResult(result)
            }

        })


    }

    private fun showUpdatingResult(result: String) {
        if (!result.isBlank()){
            Toast.makeText(context,result, Toast.LENGTH_LONG).show()
            viewModel.upDatingUserInformation.value=""
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