package com.example.rehabcalculator2.ui.calendar

import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.rehabcalculator2.App
import com.example.rehabcalculator2.database.CostByTherapist
import com.example.rehabcalculator2.database.OnetimeSchedule
import com.example.rehabcalculator2.database.ScheduleDatabaseDao
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewModel(private val sdataSource: ScheduleDatabaseDao) : ViewModel() {

    val DAYS_OF_WEEK = 7
    var LOW_OF_CALENDAR = 0

    //현재 달력에 보여줄 기준달
    lateinit var base_calendar : Calendar

    // 달력을 일요일부터 보여주도록 할 때 이번달의 1일이 무슨요일이냐에 따라
    // 왼쪽에 빈공간을 저번달의 막주 정보가 보이도록 한다.

    //현재 기준달의 1일전에 비는 공백 갯수. 기준달의 직전달의 마지막 날짜를 공백 개수만큼 가져온다.
    var prevMonthTailOffset = 0

    //현재 기준달의 마지막날 뒤로 비는 공백 갯수. 기준달의 다음달의 첫 날짜를 공백 개수만큼 가져온다.
    var nextMonthHeadOffset = 0

    //현재 기준달의 마지막 날짜
    var currentMonthMaxDate = 0

    var currentTitle : String? = null

    //그리드뷰에 그려줄 날의 숫자.
    val data = arrayListOf<Int>()

    var dataKey = arrayListOf<String>()

    var schedulesMap : ArrayMap<String, ArrayList<OnetimeSchedule>>? = null

    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")

    val title_format: DateFormat = SimpleDateFormat("YYYY.MM")

    var clickedDayKey : String? = null

    var clickedDaySchedule : OnetimeSchedule? = null

    val day_format: DateFormat = SimpleDateFormat("HH:mm")


    init {
        base_calendar = Calendar.getInstance()
    }

    fun makeMonthDate() = runBlocking {

        data.clear()
        dataKey.clear()

        base_calendar.set(Calendar.DATE, 1)

        currentMonthMaxDate = base_calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        prevMonthTailOffset = base_calendar.get(Calendar.DAY_OF_WEEK) - 1

        currentTitle = title_format.format(base_calendar.time)

        makePrevMonthTail(base_calendar.clone() as Calendar)

        makeCurrentMonth(base_calendar)

        LOW_OF_CALENDAR =6
        if((LOW_OF_CALENDAR*7 - (prevMonthTailOffset + currentMonthMaxDate)) >= 7)
            LOW_OF_CALENDAR = 5
        else
            LOW_OF_CALENDAR = 6

        nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTailOffset + currentMonthMaxDate)

        makeNextMonthHead()

        val fromCalendar = base_calendar.clone() as Calendar
        fromCalendar.add(Calendar.MONTH, -1)
        fromCalendar.set(Calendar.DATE, data[0])
        val from = fromCalendar.time

        val toCalendar = base_calendar.clone() as Calendar
        toCalendar.add(Calendar.MONTH, 1)
        toCalendar.set(Calendar.DATE, data[data.lastIndex])
        val to = toCalendar.time

        // 0. 현재 기준의 키 달력
        val searchCalendar = fromCalendar.clone() as Calendar
        while (searchCalendar <= toCalendar) {
            dataKey.add(df.format(searchCalendar.time))
            searchCalendar.add(Calendar.DATE, 1)
        }

        schedulesMap = ArrayMap()

        //1. os 맵 초기화
        val temp = sdataSource.findSchedulesBetweenDates(from, to) as ArrayList<OnetimeSchedule>

        for (i in temp) {
            initMap(i)
        }

        Log.d("hkyeom111", "os :" + schedulesMap!!.toString())


        // 2. ps -> os변환후 맵 초기화
        //val list = sdataSource.findPeriodicSchedulesBetweenDates(from, to) as ArrayList<OnetimeSchedule>

        val list = sdataSource.findPeriodicSchedulesBetweenDates(to) as ArrayList<OnetimeSchedule>
        for (i in list) {

            val startDay = Calendar.getInstance()
            startDay.time = i.startTime

            val endCal = Calendar.getInstance()
            endCal.time = i.endTime

            while (i.dayOfWeek != startDay.get(Calendar.DAY_OF_WEEK)) {
                startDay.add(Calendar.DATE, 1)
            }

            while (toCalendar >= startDay) {

                endCal.set(startDay.get(Calendar.YEAR), startDay.get(Calendar.MONTH), startDay.get(Calendar.DATE))

                val onetime_s = OnetimeSchedule(App.prefs.pref_databaseId++, i.therapistId, i.name, -1, startDay.time, endCal.time, i.price, i.monthlyFee, i.numberOfConsecutiveLectures)
                initMap(onetime_s)

                sdataSource.insert(onetime_s)
                startDay.add(Calendar.DATE, 7)

            }

            // 2. 반복 os의 시작 정보를 다음달로 변경
            i.startTime = startDay.time
            sdataSource.update(i)


            //Log.d("hkyeom111", "ps: ") // + schedulesMap!!.toString())

        }



        //Log.d("hkyeom555", "final :" + schedulesMap!!.toString())
    }

    fun getNames() : ArrayList<CostByTherapist> = runBlocking {

        val fromCalendar = base_calendar.clone() as Calendar
        fromCalendar.set(Calendar.DATE, 1)
        val from = fromCalendar.time

        val toCalendar = base_calendar.clone() as Calendar
        toCalendar.set(Calendar.DATE, currentMonthMaxDate)
        val to = toCalendar.time

        sdataSource.get_count_by_name(from, to) as ArrayList
    }


    private fun initMap(schedule: OnetimeSchedule) {
        val calendar = Calendar.getInstance()
        calendar.time = schedule.startTime
        val key = df.format(calendar.time)
        if (schedulesMap!![key] == null) {
            val list = ArrayList<OnetimeSchedule>()
            list.add(schedule)
            schedulesMap!![key] = list
        } else {
            schedulesMap!![key]?.add(schedule)

        }
    }

    private fun makePrevMonthTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxDate - prevMonthTailOffset

        for (i in 1..prevMonthTailOffset) {
            data.add(++maxOffsetDate)
        }

    }

    private fun makeCurrentMonth(calendar: Calendar) {
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) data.add(i)
    }

    private fun makeNextMonthHead() {
        var date = 1

        for (i in 1..nextMonthHeadOffset) data.add(date++)
    }

    fun getDayFormat(date : Date?): String {
        return day_format.format(date)
    }


}