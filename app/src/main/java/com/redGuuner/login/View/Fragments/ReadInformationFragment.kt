package com.redGuuner.login.View.Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.redGuuner.login.R
import com.redGuuner.login.ViewModels.ReadInformationViewModel
import kotlinx.android.synthetic.main.fragment_all_information.*


class ReadInformationFragment : Fragment(R.layout.fragment_all_information) {

    private lateinit var viewModel: ReadInformationViewModel
    private lateinit var navigator: NavController


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

    override fun onResume() {
        super.onResume()
        viewModel.getUserInformation()

        editMyInformation.setOnClickListener {
           toInsertInformationFragment()
        }

    }


    // ViewModels And LiveData
    private fun setUpViewModel() {

        viewModel = ViewModelProvider(this).get(ReadInformationViewModel::class.java)
    }

    private fun setUpObservers() {
        viewModel.userInformation.observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                userAllInfoTXT.text = data
            }

        })
    }


   // navigation
    private fun setUpNavigation(view: View) {
        navigator = Navigation.findNavController(view)

    }

    private fun toInsertInformationFragment(){
      navigator.navigate(R.id.action_allInformation_to_editInformationFragment)
    }
}
