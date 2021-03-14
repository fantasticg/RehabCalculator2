package com.example.rehabcalculator2.ui.calendar

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rehabcalculator2.MainActivity
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.ScheduleDatabase
import com.example.rehabcalculator2.databinding.FragmentCalendarBinding
import kotlinx.coroutines.launch
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel

    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = calendarViewModel.currentTitle


    }


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

        calendarViewModel =
                activity?.let {
                    ViewModelProvider(
                            it, viewModelFactory).get(CalendarViewModel::class.java)
                }!!

        binding.calendarViewModel = calendarViewModel

        binding.lifecycleOwner = this

        val manager = GridLayoutManager(context, calendarViewModel.DAYS_OF_WEEK)
        binding.calendarView.layoutManager = manager

        calendarAdapter = CalendarAdapter(sdataSource, calendarViewModel)
        calendarAdapter.itemClick = object: CalendarAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //dayFragment 호출!!
                calendarViewModel.clickedDayKey = calendarViewModel.dataKey[position]
                activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_day)
            }
        }


        binding.calendarView.adapter = calendarAdapter

        binding.calendarView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        binding.calendarView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        /*
        binding.toolbar.setOnClickListener() {
            //PickerUtils.openDatePicker(context as Context, it as androidx.appcompat.widget.Toolbar, calendarViewModel.current_calendar)

            val cal = calendarViewModel.current_calendar

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, date ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DATE, date)

                binding.toolbar.title = year.toString() + "-" + (month + 1).toString() + "-" + date.toString()

                //기준 달 변경.
                calendarViewModel.viewModelScope.launch {
                    calendarViewModel.makeMonthDate()
                }
                adapter.notifyDataSetChanged()

            }

            DatePickerDialog(context as Context, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()

        }

         */


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calendar_topappbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_add) {
            calendarViewModel.clickedDaySchedule.value = null
            activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.navigation_add)
        }

        if(item.itemId == R.id.item_temp) {
            val list = calendarViewModel.getNames()
            var toast : String = ""
            var total : Int = 0

            for(i in list) {
                toast = toast + i.name + " : " + (i.sum_of_price + i.monthly_fee) + "\n";
                total = total + (i.sum_of_price + i.monthly_fee)
            }

            toast = toast + "total : " + total

            Toast.makeText(context, toast, Toast.LENGTH_LONG).show()


        }

        if(item.itemId == android.R.id.home) {

            val cal = calendarViewModel.base_calendar

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, date ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DATE, date)

                (activity as AppCompatActivity).supportActionBar?.title = calendarViewModel.title_format.format(cal.time)

                //기준 달 변경.
                calendarViewModel.viewModelScope.launch {
                    calendarViewModel.makeMonthDate()
                }
                calendarAdapter.notifyDataSetChanged()

            }

            DatePickerDialog(context as Context, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        return super.onOptionsItemSelected(item)
    }


}