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

    /*
    //고정 스케줄 얻어오기
    @Query("SELECT * FROM schedules_table WHERE start_time BETWEEN :from AND :to AND day_of_weeks >= 0")
    suspend fun findPeriodicSchedulesBetweenDates(from: Date, to: Date): List<OnetimeSchedule>
     */

    @Query("SELECT * FROM schedules_table WHERE start_time < :to AND day_of_weeks >= 0")
    suspend fun findPeriodicSchedulesBetweenDates(to: Date): List<OnetimeSchedule>


    //일일 스케줄 얻어오기
    @Query("SELECT * FROM schedules_table WHERE start_time BETWEEN :from AND :to AND day_of_weeks < 0")
    suspend fun findSchedulesBetweenDates(from: Date, to: Date): List<OnetimeSchedule>

    //스케줄 치료사이름 얻어오기
    @Query("SELECT DISTINCT(therapist_name) FROM schedules_table")
    suspend fun get_names(): List<String>

    @Query("SELECT therapist_name, SUM(price), monthly_fee FROM schedules_table WHERE start_time BETWEEN :from AND :to AND day_of_weeks < 0 GROUP BY therapist_name")
    suspend fun get_count_by_name(from: Date, to: Date) : List<CostByTherapist>


    /*
    @Query("SELECT * FROM schedules_table")
    suspend fun getSchedule(month ): List<OnetimeSchedule>
     */

    @Query("DELETE FROM schedules_table")
    suspend fun clear()

}