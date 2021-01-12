package com.example.rehabcalculator2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface ScheduleDatabaseDao {

    @Insert
    suspend fun insert(schedule: OnetimeSchedule)

    @Update
    suspend fun update(schedule: OnetimeSchedule)

    @Query("SELECT * FROM schedules_table")
    suspend fun getAllSchedules(): List<OnetimeSchedule>

    @Query("SELECT * FROM schedules_table WHERE start_time BETWEEN :from AND :to AND day_of_weeks >= 0")
    suspend fun findPeriodicSchedulesBetweenDates(from: Date, to: Date): List<OnetimeSchedule>

    @Query("SELECT * FROM schedules_table WHERE start_time BETWEEN :from AND :to AND day_of_weeks < 0")
    suspend fun findSchedulesBetweenDates(from: Date, to: Date): List<OnetimeSchedule>


    /*
    @Query("SELECT * FROM schedules_table")
    suspend fun getSchedule(month ): List<OnetimeSchedule>
     */

    @Query("DELETE FROM schedules_table")
    suspend fun clear()

}