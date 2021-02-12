package com.example.rehabcalculator2.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.ScheduleDatabase
import com.example.rehabcalculator2.databinding.FragmentDayBinding

class DayFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel

    lateinit var dayAdapter : DayAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentDayBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_day, container, false)

        val application = requireNotNull(this.activity).application

        val sdataSource = ScheduleDatabase.getInstance(application).scheduleDatabaseDao

        val viewModelFactory = CalendarViewModelFactory(sdataSource)

        calendarViewModel =
                activity?.let {
                    ViewModelProvider(
                            it, viewModelFactory).get(CalendarViewModel::class.java)
                }!!

        binding.calendarViewModel = calendarViewModel

        binding.lifecycleOwner = this

        val manager = LinearLayoutManager(context)
        binding.dayView.layoutManager = manager

        dayAdapter = DayAdapter(sdataSource, calendarViewModel)

        dayAdapter.itemClick = object: DayAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //edit
                calendarViewModel.clickedDaySchedule = calendarViewModel.schedulesMap!!.get(calendarViewModel.clickedDayKey)!![position]
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_add)
            }
        }

        binding.dayView.adapter = dayAdapter

        return binding.root

    }

}