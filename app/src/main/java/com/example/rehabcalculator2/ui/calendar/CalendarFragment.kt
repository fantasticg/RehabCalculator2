package com.example.rehabcalculator2.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.PeriodicScheduleDatabase
import com.example.rehabcalculator2.database.ScheduleDatabase
import com.example.rehabcalculator2.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel

    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCalendarBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_calendar, container, false)

        val application = requireNotNull(this.activity).application

        val sdataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao
        val pdataSource = PeriodicScheduleDatabase.getInstance(application).periodscheduleDatabaseDao

        val viewModelFactory = CalendarViewModelFactory(sdataSource, pdataSource)

        val calendarViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(CalendarViewModel::class.java)

        binding.calendarViewModel = calendarViewModel

        binding.lifecycleOwner = this

        val manager = GridLayoutManager(context, calendarViewModel.DAYS_OF_WEEK)
        binding.calendarView.layoutManager = manager

        val adapter = CalendarAdapter(sdataSource, pdataSource, calendarViewModel)

        binding.calendarView.adapter = adapter

        return binding.root
    }


}