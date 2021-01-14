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
data class CostByTherapist(

    @ColumnInfo(name = "therapist_name")            // 치료사 이름
    var name: String = "a",

    @ColumnInfo(name = "sum_of_price")                     // 회당 비용
    var price: Int,

    @ColumnInfo(name = "monthly_fee")               // 월회비
    var monthlyFee: Int


)
