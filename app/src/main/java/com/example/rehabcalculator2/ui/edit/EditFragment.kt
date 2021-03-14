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

package com.example.rehabcalculator2.ui.edit

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.ScheduleDatabase
import com.example.rehabcalculator2.databinding.FragmentAddBinding
import com.example.rehabcalculator2.ui.PickerUtils
import com.example.rehabcalculator2.ui.add.AddViewModel
import com.example.rehabcalculator2.ui.add.AddViewModelFactory
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditFragment : Fragment() {

    private lateinit var addViewModel : AddViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAddBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add, container, false)

        val application = requireNotNull(this.activity).application
        //val arguments = SleepDetailFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val sdataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val viewModelFactory = AddViewModelFactory(sdataSource)

        activity?.actionBar?.hide()

        // Get a reference to the ViewModel associated with this fragment.
        addViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(AddViewModel::class.java)

        addViewModel.saved.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it == true) {
                activity?.onBackPressed()
                addViewModel.saved.value = false

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

            }

        })


        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.addViewModel = addViewModel

        //initialize
        binding.isFixChecked = true
        binding.isFixMon = true

        //mon
        binding.monStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.monEndTimeButton, addViewModel.monStartCal, addViewModel.monEndCal)
        }

        binding.monEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.monEndCal, addViewModel.monStartCal)
        }

        binding.monContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }

        //tue
        binding.tueStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.tueEndTimeButton, addViewModel.tueStartCal, addViewModel.tueEndCal)
        }

        binding.tueEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.tueEndCal, addViewModel.tueStartCal)
        }

        binding.tueContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }

        //wed
        binding.wedStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.wedEndTimeButton, addViewModel.wedStartCal, addViewModel.wedEndCal)
        }

        binding.wedEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.wedEndCal, addViewModel.wedStartCal)
        }

        binding.wedContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }

        //thu
        binding.thuStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.thuEndTimeButton, addViewModel.thuStartCal, addViewModel.thuEndCal)
        }

        binding.thuEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.thuEndCal, addViewModel.thuStartCal)
        }

        binding.thuContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }

        //fri
        binding.friStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.friEndTimeButton, addViewModel.friStartCal, addViewModel.friEndCal)
        }

        binding.friEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.friEndCal, addViewModel.friStartCal)
        }

        binding.friContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }

        //sat
        binding.satStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.satEndTimeButton, addViewModel.satStartCal, addViewModel.satEndCal)
        }

        binding.satEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.satEndCal, addViewModel.satStartCal)
        }

        binding.satContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }

        //sat
        binding.sunStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.sunEndTimeButton, addViewModel.sunStartCal, addViewModel.sunEndCal)
        }

        binding.sunEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.sunEndCal, addViewModel.sunStartCal)
        }

        binding.sunContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }


        //onetime
        binding.onetimeDateButton.setOnClickListener {
            //openDatePicker(ctx :Context, button : Button, startCal : Calendar, endCal : Calendar) {
            context?.let { it1 -> PickerUtils.openDatePicker(it1, it as Button, addViewModel.onetimeStartCal, addViewModel.onetimeEndCal) }
        }

        binding.onetimeStartTimeButton.setOnClickListener {
            openStartTimePicker(it as Button, binding.onetimeEndTimeButton, addViewModel.onetimeStartCal, addViewModel.onetimeEndCal)
        }

        binding.onetimeEndTimeButton.setOnClickListener {
            openEndTimePicker(it as Button, addViewModel.onetimeEndCal, addViewModel.onetimeStartCal)
        }

        binding.onetimeContinuesButton.setOnClickListener {
            openConnectionsPopupMenu(it as Button)
        }


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

    fun openStartTimePicker(button: Button, endtime_button: Button, cal : Calendar, end_cal : Calendar) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            end_cal.set(Calendar.HOUR_OF_DAY, hour)
            end_cal.set(Calendar.MINUTE, minute+50) //50분 추가

            //button.text = SimpleDateFormat("a h:mm").format(cal.time)
            button.text = addViewModel.setButtonTime(cal)
            endtime_button.text = addViewModel.setButtonTime(end_cal)
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
    }

    fun openEndTimePicker(button: Button, cal : Calendar, start_cal : Calendar) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            if(cal.time < start_cal.time ) {
                Toast.makeText(context, "종료시간은 시작시간 이후로 설정해 주세요", Toast.LENGTH_LONG).show()
            } else {
                //button.text = SimpleDateFormat("a h:mm").format(cal.time)
                button.text = addViewModel.setButtonTime(cal)
            }
        }

        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
    }

    /*
    fun openDatePicker(button : Button, cal : Calendar) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, date ->

            addViewModel.onetimeStartCal.set(Calendar.YEAR, year)
            addViewModel.onetimeStartCal.set(Calendar.MONTH, month)
            addViewModel.onetimeStartCal.set(Calendar.DATE, date)

            addViewModel.onetimeEndCal.set(Calendar.YEAR, year)
            addViewModel.onetimeEndCal.set(Calendar.MONTH, month)
            addViewModel.onetimeEndCal.set(Calendar.DATE, date)

            button.text = year.toString()+"-"+(month+1).toString()+"-"+date.toString()
        }

        DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

     */

    fun openConnectionsPopupMenu(button: Button) {
        val popup = PopupMenu(context, button)
        popup.menuInflater.inflate(R.menu.connections, popup.menu)

        var numOfCOnnections : Int = 1;

        popup.setOnMenuItemClickListener {
            item -> when(item.itemId) {

                    R.id.connections_one -> numOfCOnnections = 1
                    R.id.connections_two -> numOfCOnnections = 2
                    R.id.connections_three -> numOfCOnnections = 3
                    else -> numOfCOnnections = 1
            }

            when(button.id) {
                R.id.mon_continues_button -> addViewModel.monNumOfConnections = numOfCOnnections
                R.id.tue_continues_button -> addViewModel.tueNumOfConnections = numOfCOnnections
                R.id.wed_continues_button -> addViewModel.wedNumOfConnections = numOfCOnnections
                R.id.thu_continues_button -> addViewModel.thuNumOfConnections = numOfCOnnections
                R.id.fri_continues_button -> addViewModel.friNumOfConnections = numOfCOnnections
                R.id.sat_continues_button -> addViewModel.satNumOfConnections = numOfCOnnections
                R.id.sun_continues_button -> addViewModel.sunNumOfConnections = numOfCOnnections
                R.id.onetime_continues_button -> addViewModel.onetimeNumOfConnections = numOfCOnnections
            }

            button.text = context?.let {
                addViewModel.setButtonConnections(numOfCOnnections,
                    it
                )
            }

            false
        }

        popup.show()

    }

}
