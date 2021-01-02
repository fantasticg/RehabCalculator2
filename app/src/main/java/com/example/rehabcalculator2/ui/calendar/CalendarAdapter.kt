package com.example.rehabcalculator2.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rehabcalculator2.database.OnetimeSchedule
import com.example.rehabcalculator2.database.PeriodicScheduleDatabaseDao
import com.example.rehabcalculator2.database.ScheduleDatabaseDao
import com.example.rehabcalculator2.databinding.ListItemScheduleBinding

class CalendarAdapter(/*val clickListener: SleepNightListener, */ val sdatabase : ScheduleDatabaseDao, val pdatabase : PeriodicScheduleDatabaseDao, val viewModel : CalendarViewModel) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(/* DayCallback() */) {

        init {
                viewModel.makeMonthDate()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                //val item = DataItem.DayItem()
                return ViewHolder.from(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                when(holder) {
                        is ViewHolder -> {

                                //val scheduleItem = getItem(position) as DataItem.DayItem
                                //holder.bind(scheduleItem.schedule)

                                if (position % viewModel.DAYS_OF_WEEK == 0) holder.tv_date.setTextColor(Color.parseColor("#ff1200"))
                                else holder.tv_date.setTextColor(Color.parseColor("#676d6e"))

                                if (position < viewModel.prevMonthTailOffset || position >= viewModel.prevMonthTailOffset + viewModel.currentMonthMaxDate) {
                                        holder.tv_date.alpha = 0.3f
                                } else {
                                        holder.tv_date.alpha = 1f
                                }
                                holder.tv_date.text = viewModel.data[position].toString()

                        }
                }

        }

        override fun getItemCount(): Int {
                return viewModel.LOW_OF_CALENDAR * viewModel.DAYS_OF_WEEK
        }

        class ViewHolder private constructor(val binding: ListItemScheduleBinding)
                : RecyclerView.ViewHolder(binding.root) {

                val tv_date = binding.tvDate

                fun bind(item: OnetimeSchedule) {
                        binding.schedule = item
                        binding.executePendingBindings()
                }

                companion object {
                        fun from(parent: ViewGroup): ViewHolder {

                                //val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_schedule, parent, false)

                                val layoutInflater = LayoutInflater.from(parent.context)
                                val binding = ListItemScheduleBinding.inflate(layoutInflater, parent, false)

                                return ViewHolder(binding)
                        }
                }
        }


}
/*
class DayCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
        }
}


sealed class DataItem {
        data class DayItem(val schedule: OnetimeSchedule): DataItem() {
                override val id = schedule.databaseId // temp
        }

        object Header: DataItem() {
                override val id = Long.MIN_VALUE
        }

        abstract val id: Long
}

 */