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

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rehabcalculator2.ObservableViewModel
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.OnetimeSchedule
import com.example.rehabcalculator2.database.PeriodicSchedule
import com.example.rehabcalculator2.database.PeriodicScheduleDatabaseDao
import com.example.rehabcalculator2.database.ScheduleDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class AddViewModel(
    sdataSource: ScheduleDatabaseDao, pdataSource: PeriodicScheduleDatabaseDao) : ObservableViewModel() {

    val sdatabase = sdataSource

    val pdatabase = pdataSource

    val therapistName = MutableLiveData<String>()

    val price = MutableLiveData<String>()

    val montlyMembershipFee = MutableLiveData<String>()



    var monStartCal: Calendar = Calendar.getInstance()

    var monEndCal: Calendar = Calendar.getInstance()

    var monNumOfConnections : Int = 1


    var tueStartCal: Calendar = Calendar.getInstance()

    var tueEndCal: Calendar = Calendar.getInstance()

    var tueNumOfConnections : Int = 1


    var wedStartCal: Calendar = Calendar.getInstance()

    var wedEndCal: Calendar = Calendar.getInstance()

    var wedNumOfConnections : Int = 1


    var thuStartCal: Calendar = Calendar.getInstance()

    var thuEndCal: Calendar = Calendar.getInstance()

    var thuNumOfConnections : Int = 1


    var friStartCal: Calendar = Calendar.getInstance()

    var friEndCal: Calendar = Calendar.getInstance()

    var friNumOfConnections : Int = 1


    var satStartCal: Calendar = Calendar.getInstance()

    var satEndCal: Calendar = Calendar.getInstance()

    var satNumOfConnections : Int = 1


    var onetimeStartCal: Calendar = Calendar.getInstance()

    var onetimeEndCal: Calendar = Calendar.getInstance()

    var onetimeNumOfConnections : Int = 1


    var databaseIdCount : Long = 1L

    init {
        monStartCal.set(Calendar.HOUR_OF_DAY, 16)
        monStartCal.set(Calendar.MINUTE, 0)
        monEndCal.set(Calendar.HOUR_OF_DAY, 16)
        monEndCal.set(Calendar.MINUTE, 50)


        tueStartCal.set(Calendar.HOUR_OF_DAY, 16)
        tueStartCal.set(Calendar.MINUTE, 0)
        tueEndCal.set(Calendar.HOUR_OF_DAY, 16)
        tueEndCal.set(Calendar.MINUTE, 50)

        wedStartCal.set(Calendar.HOUR_OF_DAY, 16)
        wedStartCal.set(Calendar.MINUTE, 0)
        wedEndCal.set(Calendar.HOUR_OF_DAY, 16)
        wedEndCal.set(Calendar.MINUTE, 50)

        thuStartCal.set(Calendar.HOUR_OF_DAY, 16)
        thuStartCal.set(Calendar.MINUTE, 0)
        thuEndCal.set(Calendar.HOUR_OF_DAY, 16)
        thuEndCal.set(Calendar.MINUTE, 50)

        friStartCal.set(Calendar.HOUR_OF_DAY, 16)
        friStartCal.set(Calendar.MINUTE, 0)
        friEndCal.set(Calendar.HOUR_OF_DAY, 16)
        friEndCal.set(Calendar.MINUTE, 50)

        satStartCal.set(Calendar.HOUR_OF_DAY, 16)
        satStartCal.set(Calendar.MINUTE, 0)
        satEndCal.set(Calendar.HOUR_OF_DAY, 16)
        satEndCal.set(Calendar.MINUTE, 50)

        onetimeStartCal.set(Calendar.HOUR_OF_DAY, 16)
        onetimeStartCal.set(Calendar.MINUTE, 0)
        onetimeEndCal.set(Calendar.HOUR_OF_DAY, 16)
        onetimeEndCal.set(Calendar.MINUTE, 50)

    }

    fun setButtonTime(cal : Calendar) : String {
        return SimpleDateFormat("a h:mm").format(cal.time)
    }

    fun setButtonConnections(numOfConnections : Int, context : Context) : String {
        when (numOfConnections) {
            1 -> return context.getString(R.string.menu_connections_one)
            2 -> return context.getString(R.string.menu_connections_two)
            3 -> return context.getString(R.string.menu_connections_three)
        }
        return context.getString(R.string.menu_connections_one)
    }

    fun setButtonDate(cal : Calendar) : String {
        return cal.get(Calendar.YEAR).toString()+"-"+(cal.get(Calendar.MONTH)+1).toString()+"-"+cal.get(Calendar.DAY_OF_MONTH).toString()
    }


    fun save(fixChecked : Boolean, context : Context) {

        if(therapistName.value.isNullOrEmpty() || price.value.isNullOrEmpty()) {
            Toast.makeText(context, R.string.add_valid_name_and_price, Toast.LENGTH_LONG).show()
            return
        }

        if(fixChecked) {
            viewModelScope.launch {
                periodic_insert()
            }

        } else {
            viewModelScope.launch {
                onetime_insert()
                withContext(Dispatchers.IO) {
                    var night = sdatabase.getSchedule()
                    Log.d("aaa", night.toString())
                }

            }
        }

    }

    private suspend fun periodic_insert() {
        val periodic_mon = PeriodicSchedule(databaseIdCount++, 0L, therapistName.value!!, 0, onetimeStartCal.timeInMillis, onetimeEndCal.timeInMillis, Integer.parseInt(price.value!!), Integer.parseInt(montlyMembershipFee.value!!), monNumOfConnections)
        withContext(Dispatchers.IO) {
            pdatabase.insert(periodic_mon)
        }

    }

    private suspend fun onetime_insert() {

        val onetime_s = OnetimeSchedule(databaseIdCount++, 0L, therapistName.value!!, onetimeStartCal.timeInMillis, onetimeEndCal.timeInMillis, Integer.parseInt(price.value!!), onetimeNumOfConnections)
        withContext(Dispatchers.IO) {
            sdatabase.insert(onetime_s);
        }

    }


}

 