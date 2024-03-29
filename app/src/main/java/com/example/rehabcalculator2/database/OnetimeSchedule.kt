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

package com.example.rehabcalculator2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "schedules_table")
data class OnetimeSchedule(

        // 일회성) 치료사, 금액, 시작시간, 종료시간, 연강여부
    @PrimaryKey(autoGenerate = true)
    var databaseId: Long = 0L,                      // dbId

    @ColumnInfo(name = "therapist_id")              // 치료사번호
    var therapistId: Long = 1,

    @ColumnInfo(name = "therapist_name")            // 치료사 이름
    var name: String = "a",

    @ColumnInfo(name = "day_of_weeks")              // 반복 요일
    var dayOfWeek: Int = -1,

    @ColumnInfo(name = "start_time")                // 날짜 및 시작시간
    var startTime: Date?,

    @ColumnInfo(name = "end_time")                  // 종료시간
    var endTime: Date?,

    @ColumnInfo(name = "price")                     // 회당 비용
    var price: Int,

    @ColumnInfo(name = "monthly_fee")               // 월회비
    var monthlyFee: Int,

    @ColumnInfo(name = "number_of_consecutive_lectures")
    var numberOfConsecutiveLectures: Int = 1        // 연결치료여부

)
