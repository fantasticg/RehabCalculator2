package com.example.rehabcalculator2.ui.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.ScheduleDatabase
import com.example.rehabcalculator2.databinding.FragmentCalendarBinding
import com.example.rehabcalculator2.ui.PickerUtils

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

        val viewModelFactory = CalendarViewModelFactory(sdataSource)

        val calendarViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(CalendarViewModel::class.java)

        binding.calendarViewModel = calendarViewModel

        binding.lifecycleOwner = this

        val manager = GridLayoutManager(context, calendarViewModel.DAYS_OF_WEEK)
        binding.calendarView.layoutManager = manager

        val adapter = CalendarAdapter(sdataSource, calendarViewModel)

        binding.calendarView.adapter = adapter

        binding.calendarView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        binding.calendarView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        binding.toolbar.setOnClickListener() {
            PickerUtils.openDatePicker(context as Context, it as androidx.appcompat.widget.Toolbar, calendarViewModel.current_calendar)
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }


}