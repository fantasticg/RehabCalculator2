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
import com.example.rehabcalculator2.App
import com.example.rehabcalculator2.ObservableViewModel
import com.example.rehabcalculator2.R
import com.example.rehabcalculator2.database.OnetimeSchedule
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
        sdataSource: ScheduleDatabaseDao) : ObservableViewModel() {

    val saved = MutableLiveData<Boolean>()

    val sdatabase = sdataSource

    val therapistName = MutableLiveData<String>()

    val price = MutableLiveData<String>()

    var montlyMembershipFee = MutableLiveData<String>()

    var monStartCal: Calendar = Calendar.getInstance()

    var monEndCal: Calendar = Calendar.getInstance()

    var monNumOfConnections : Int = 1

    val monScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.MONDAY


    var tueStartCal: Calendar = Calendar.getInstance()

    var tueEndCal: Calendar = Calendar.getInstance()

    var tueNumOfConnections : Int = 1

    val tueScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.TUESDAY



    var wedStartCal: Calendar = Calendar.getInstance()

    var wedEndCal: Calendar = Calendar.getInstance()

    var wedNumOfConnections : Int = 1

    val wedScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.WEDNESDAY



    var thuStartCal: Calendar = Calendar.getInstance()

    var thuEndCal: Calendar = Calendar.getInstance()

    var thuNumOfConnections : Int = 1

    val thuScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.THURSDAY


    var friStartCal: Calendar = Calendar.getInstance()

    var friEndCal: Calendar = Calendar.getInstance()

    var friNumOfConnections : Int = 1

    val friScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.FRIDAY


    var satStartCal: Calendar = Calendar.getInstance()

    var satEndCal: Calendar = Calendar.getInstance()

    var satNumOfConnections : Int = 1

    val satScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.SATURDAY



    var sunStartCal: Calendar = Calendar.getInstance()

    var sunEndCal: Calendar = Calendar.getInstance()

    var sunNumOfConnections : Int = 1

    val sunScheduleDayOfWeek : ScheduleDayOfWeek = ScheduleDayOfWeek.SUNDAY



    var onetimeStartCal: Calendar = Calendar.getInstance()

    var onetimeEndCal: Calendar = Calendar.getInstance()

    var onetimeNumOfConnections : Int = 1


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

        sunStartCal.set(Calendar.HOUR_OF_DAY, 16)
        sunStartCal.set(Calendar.MINUTE, 0)
        sunEndCal.set(Calendar.HOUR_OF_DAY, 16)
        sunEndCal.set(Calendar.MINUTE, 50)


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


    fun save(fixChecked : Boolean, fixedMon : Boolean?, fixedTue : Boolean?, fixedWed : Boolean?, fixedThu : Boolean?, fixedFri : Boolean?, fixedSat : Boolean?, fixedSun : Boolean?, context : Context) {

        if(therapistName.value.isNullOrEmpty() || price.value.isNullOrEmpty()) {
            Toast.makeText(context, R.string.add_valid_name_and_price, Toast.LENGTH_LONG).show()
            return
        }

        if(fixChecked) {
            viewModelScope.launch {
                periodic_insert(fixedMon ?: false, fixedTue ?: false, fixedWed ?: false, fixedThu ?: false, fixedFri ?: false, fixedSat ?: false, fixedSun ?: false)
                withContext(Dispatchers.IO) {
                    var night = sdatabase.getAllSchedules()
                    Log.d("bbb", night.toString())

                }


            }

        } else {
            viewModelScope.launch {
                onetime_insert()
                withContext(Dispatchers.IO) {
                    var night = sdatabase.getAllSchedules()
                    Log.d("aaa", night.toString())
                }

            }
        }

    }

    private suspend fun periodic_insert(fixedMon: Boolean, fixedTue: Boolean, fixedWed: Boolean, fixedThu: Boolean, fixedFri: Boolean, fixedSat: Boolean, fixedSun: Boolean) {

        var mFeeOrZero = 0

        if(!montlyMembershipFee.value.isNullOrEmpty()) {
            mFeeOrZero = Integer.parseInt(montlyMembershipFee.value)
        }
        withContext(Dispatchers.IO) {
            if (fixedMon) {

                val periodic_mon = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, monScheduleDayOfWeek.number, monStartCal.time, monEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, monNumOfConnections)
                sdatabase.insert(periodic_mon);

            }

            if (fixedTue) {

                val periodic_tue = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, tueScheduleDayOfWeek.number, tueStartCal.time, tueEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, tueNumOfConnections)
                //withContext(Dispatchers.IO) {
                sdatabase.insert(periodic_tue);
                //}

            }

            if (fixedWed) {

                val periodic_wed = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, wedScheduleDayOfWeek.number, wedStartCal.time, wedEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, wedNumOfConnections)
                //withContext(Dispatchers.IO) {
                sdatabase.insert(periodic_wed);
                //}

            }

            if (fixedThu) {

                val periodic_thu = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, thuScheduleDayOfWeek.number, thuStartCal.time, thuEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, thuNumOfConnections)
                //withContext(Dispatchers.IO) {
                sdatabase.insert(periodic_thu);
                //}

            }

            if (fixedFri) {

                val periodic_fri = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, friScheduleDayOfWeek.number, friStartCal.time, friEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, friNumOfConnections)
                //withContext(Dispatchers.IO) {
                sdatabase.insert(periodic_fri);
                //}

            }

            if (fixedSat) {

                val periodic_sat = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, satScheduleDayOfWeek.number, satStartCal.time, satEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, satNumOfConnections)
                //withContext(Dispatchers.IO) {
                sdatabase.insert(periodic_sat);
                //}

            }

            if (fixedSun) {

                val periodic_sun = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, sunScheduleDayOfWeek.number, sunStartCal.time, sunEndCal.time, Integer.parseInt(price.value!!), mFeeOrZero, sunNumOfConnections)
                //withContext(Dispatchers.IO) {
                sdatabase.insert(periodic_sun);
                //}

            }

            saved.postValue(true)

        }

    }

    private suspend fun onetime_insert() {

        val onetime_s = OnetimeSchedule(App.prefs.pref_databaseId++, 0L, therapistName.value!!, -1, onetimeStartCal.time, onetimeEndCal.time, Integer.parseInt(price.value!!), -1, onetimeNumOfConnections)
        withContext(Dispatchers.IO) {
            sdatabase.insert(onetime_s);
        }

    }


}

 