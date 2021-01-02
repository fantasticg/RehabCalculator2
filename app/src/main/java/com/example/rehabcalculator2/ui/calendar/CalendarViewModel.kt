package com.example.rehabcalculator2.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.rehabcalculator2.database.PeriodicScheduleDatabaseDao
import com.example.rehabcalculator2.database.ScheduleDatabaseDao
import java.util.*

class CalendarViewModel(private val sdataSource: ScheduleDatabaseDao,
                        private val pdataSource: PeriodicScheduleDatabaseDao) : ViewModel() {

    val DAYS_OF_WEEK = 7
    val LOW_OF_CALENDAR =6

    val calendar = Calendar.getInstance()

    var prevMonthTailOffset = 0

    var nextMonthHeadOffset = 0

    var currentMonthMaxDate = 0

    var data = arrayListOf<Int>()

    init {
        calendar.time = Date()
    }

    fun makeMonthDate() {

        data.clear()

        calendar.set(Calendar.DATE, 1)

        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        prevMonthTailOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1

        makePrevMonthTail(calendar.clone() as Calendar)

        makeCurrentMonth(calendar)

        nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTailOffset + currentMonthMaxDate)
        makeNextMonthHead()

    }

    private fun makePrevMonthTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxDate - prevMonthTailOffset

        for (i in 1..prevMonthTailOffset) data.add(++maxOffsetDate)
    }

    private fun makeCurrentMonth(calendar: Calendar) {
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) data.add(i)
    }

    private fun makeNextMonthHead() {
        var date = 1

        for (i in 1..nextMonthHeadOffset) data.add(date++)
    }

}