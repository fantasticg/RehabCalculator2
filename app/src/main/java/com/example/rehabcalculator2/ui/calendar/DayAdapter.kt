package com.example.rehabcalculator2.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rehabcalculator2.database.ScheduleDatabaseDao
import com.example.rehabcalculator2.databinding.ListItemDayBinding

class DayAdapter(val sdatabase : ScheduleDatabaseDao, val viewModel : CalendarViewModel) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(/* DayCallback() */) {
        var itemClick : ItemClick? = null
        val sc_list = viewModel.schedulesMap!!.get(viewModel.clickedDayKey)!!

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                //val item = DataItem.DayItem()

                return ViewHolder.from(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



                when(holder) {
                        is ViewHolder -> {

                                if(itemClick != null) {
                                        holder?.itemView?.setOnClickListener { v -> itemClick?.onClick(v, position)}
                                }

                                //holder.tv_sc1.text =sc_list[0]!!.name
                                holder.therapist.text = sc_list[position].name
                                holder.time.text = viewModel.getDayFormat(sc_list[position].startTime) + "-" +  viewModel.getDayFormat(sc_list[position].endTime)


                        }
                }

        }

        override fun getItemCount(): Int {
                return sc_list.size
        }

        class ViewHolder private constructor(val binding: ListItemDayBinding)
                : RecyclerView.ViewHolder(binding.root) {

                //val tv_date = binding.tvDate

                val therapist = binding.daylistTherapist;
                val time = binding.daylistTime;
                companion object {
                        fun from(parent: ViewGroup): ViewHolder {

                                val layoutInflater = LayoutInflater.from(parent.context)
                                val binding = ListItemDayBinding.inflate(layoutInflater, parent, false)

                                return ViewHolder(binding)
                        }
                }
        }


        interface ItemClick {
                fun onClick(view: View, position: Int)
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