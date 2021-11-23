package com.joesemper.timetable.ui.classes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.databinding.ItemHomeworkBinding
import com.joesemper.timetable.util.getDaysLeft
import com.joesemper.timetable.util.getRandomSecondaryIcon

class HomeHwRvAdapter(private val data: List<Lesson>) :
    RecyclerView.Adapter<HomeHwRvAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHomeworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lesson) {
            with(binding) {
                tvLessonTitle.text = item.name
                item.homework.also { tvHomework.text = it }
                (getDaysLeft(item.startAt).toString() + " days left").also { tvDaysLeft.text = it }
                ivLessonIcon.setImageResource(item.icon)
                ivIcon1.setImageResource(getRandomSecondaryIcon())
                ivIcon2.setImageResource(getRandomSecondaryIcon())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeworkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(data[position])
    }

    override fun getItemCount() = data.size
}