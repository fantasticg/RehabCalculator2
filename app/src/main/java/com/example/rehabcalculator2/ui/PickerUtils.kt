package com.example.rehabcalculator2.ui

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import java.util.*

class PickerUtils {
    companion object {

        fun openDatePicker(ctx: Context, tv: TextView, startCal: Calendar, endCal: Calendar) {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, date ->

                startCal.set(Calendar.YEAR, year)
                startCal.set(Calendar.MONTH, month)
                startCal.set(Calendar.DATE, date)

                endCal.set(Calendar.YEAR, year)
                endCal.set(Calendar.MONTH, month)
                endCal.set(Calendar.DATE, date)

                tv.text = year.toString() + "-" + (month + 1).toString() + "-" + date.toString()
            }

            DatePickerDialog(ctx, dateSetListener, startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH)).show()
        }

        fun openDatePicker(ctx: Context, tbar: Toolbar, cal: Calendar) {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, date ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DATE, date)

                tbar.title = year.toString() + "-" + (month + 1).toString() + "-" + date.toString()
            }

            DatePickerDialog(ctx, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

    }
}
