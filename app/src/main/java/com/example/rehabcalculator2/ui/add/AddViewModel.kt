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

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.rehabcalculator2.ObservableViewModel
import com.example.rehabcalculator2.database.OnetimeSchedule
import com.example.rehabcalculator2.database.PeriodicSchedule
import com.example.rehabcalculator2.database.PeriodicScheduleDatabaseDao
import com.example.rehabcalculator2.database.ScheduleDatabaseDao

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class AddViewModel(
    sdataSource: ScheduleDatabaseDao, pdataSource: PeriodicScheduleDatabaseDao) : ObservableViewModel() {

    val sdatabase = sdataSource

    val pdatabase = pdataSource

    val test ="abcdef"

    val therapistName = MutableLiveData<String>()


    /*

    /*
    /** Coroutine setup variables */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    */
    private val night = MediatorLiveData<OnetimeSchedule>()

    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
    }

    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker
    /*
    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    */

    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
*/

    private suspend fun periodic_insert() {
        val periodic_s = PeriodicSchedule(1L, 2L, "a", 0, 3L, 45, 50000, 1)
        pdatabase.insert(periodic_s);
    }

    private suspend fun onetime_insert() {
        val onetime_s = OnetimeSchedule(1L, 2L, "a", 3L,4L, 45, 50000, 1)
        sdatabase.insert(onetime_s);
    }

}

 