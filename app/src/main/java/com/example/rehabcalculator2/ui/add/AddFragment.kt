/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.rehabcalculator2.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.PeriodicScheduleDatabase
import com.example.rehabcalculator2.database.ScheduleDatabase
import com.example.rehabcalculator2.databinding.FragmentAddBinding


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add, container, false)


        val application = requireNotNull(this.activity).application
        //val arguments = SleepDetailFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val sdataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val pdataSource = PeriodicScheduleDatabase.getInstance(application).periodscheduleDatabaseDao
        val viewModelFactory = AddViewModelFactory(sdataSource, pdataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val addViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(AddViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.addViewModel = addViewModel
/*

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        addViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                        //SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                //sleepDetailViewModel.doneNavigating()
            }
        })

         */

        return binding.root
    }
}
