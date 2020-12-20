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

    /*
    //특정 기간의 치료사별 치료비 합
    @Query("SELECT SUM(price) from schedules_table WHERE therapist_id = :therapistId")
    fun getPrice(therapistId: Long, startTime: Long, treatmentTime: Int) : Int

    //특정 기간의 치료사별 치료 횟수
    @Query("SELECT COUNT(*) from schedules_table WHERE therapist_id = :therapistId")
    fun getCount(therapistId: Long, startTime: Long, treatmentTime: Int) : Int

    @Query("DELETE FROM schedules_table")
    suspend fun clear()
    */
}