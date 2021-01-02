package com.example.rehabcalculator2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PeriodicScheduleDatabaseDao {
    @Insert
    suspend fun insert(schedule: PeriodicSchedule)

    @Update
    suspend fun update(schedule: PeriodicSchedule)

    @Query("SELECT * FROM periodic_schedules_table")
    suspend fun getPSchedule(): List<PeriodicSchedule>

}