package com.example.rehabcalculator2.ui.calendar

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.rehabcalculator2.database.OnetimeSchedule
import com.example.rehabcalculator2.database.ScheduleDatabaseDao
import com.example.rehabcalculator2.databinding.ListItemScheduleBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CalendarAdapter(/*val clickListener: SleepNightListener, */ val sdatabase : ScheduleDatabaseDao, val viewModel : CalendarViewModel) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(/* DayCallback() */) {

        init {
                viewModel.viewModelScope.launch {
                        viewModel.makeMonthDate()
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                //val item = DataItem.DayItem()

                return ViewHolder.from(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                when(holder) {
                        is ViewHolder -> {

                                if(position == 0 ) {
                                        Log.d("hkyeom111", "onBindViewHolder")
                                }

                                //val scheduleItem = getItem(position) as DataItem.DayItem
                                //holder.bind(scheduleItem.schedule)

                                //날짜 글씨 색상 및 알파 값 설정
                                if (position % viewModel.DAYS_OF_WEEK == 0) holder.tv_date.setTextColor(Color.parseColor("#ff1200"))
                                else holder.tv_date.setTextColor(Color.parseColor("#676d6e"))

                                if (position < viewModel.prevMonthTailOffset || position >= viewModel.prevMonthTailOffset + viewModel.currentMonthMaxDate) {
                                        holder.tv_date.alpha = 0.3f
                                        holder.tv_sc1.alpha = 0.3f
                                        holder.tv_sc2.alpha = 0.3f
                                        holder.tv_sc3.alpha = 0.3f
                                        holder.tv_count.alpha = 0.3f

                                } else {
                                        holder.tv_date.alpha = 1f
                                }
                                holder.tv_date.text = viewModel.data[position].toString()

                                //달력뷰에 스케줄 텍스트 추가

                                if(viewModel.schedulesMap!!.get(viewModel.dataKey[position]) != null) {

                                        val  sc_list = viewModel.schedulesMap!!.get(viewModel.dataKey[position])!!

                                        sc_list.sortBy { onetimeSchedule -> onetimeSchedule.startTime }

                                        if(sc_list.size > 0)
                                                holder.tv_sc1.text =sc_list[0]!!.name
                                        if(sc_list.size > 1)
                                                holder.tv_sc2.text = sc_list[1]!!.name
                                        if(sc_list.size > 2)
                                                holder.tv_sc3.text = sc_list[2]!!.name
                                        if(sc_list.size > 3)
                                                holder.tv_count.text = "+"+((sc_list.size - 3).toString())
                                }

                        }
                }

        }

        override fun getItemCount(): Int {
                return viewModel.LOW_OF_CALENDAR * viewModel.DAYS_OF_WEEK
        }

        class ViewHolder private constructor(val binding: ListItemScheduleBinding)
                : RecyclerView.ViewHolder(binding.root) {

                val tv_date = binding.tvDate
                val tv_sc1 = binding.tvSc1
                val tv_sc2 = binding.tvSc2
                val tv_sc3 = binding.tvSc3
                val tv_count = binding.tvCount


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
class DayCallback : DiffUtil.ItemCallback<DataItem>() {`
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