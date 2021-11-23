package com.joesemper.timetable.ui.classes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.databinding.ItemLessonBinding
import com.joesemper.timetable.util.getCurrentLessonPosition
import com.joesemper.timetable.util.getLessonTime

class HomeLessonsRvAdapter(private val data: List<Lesson>) :
    RecyclerView.Adapter<HomeLessonsRvAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lesson, isCurrent: Boolean) {
            with(binding) {
                tvLessonTitle.text = item.name
                tvLessonTime.text = getLessonTime(item)
                videoLayout.visibility = if (isCurrent) View.VISIBLE else View.GONE
                ivLessonIcon.setImageResource(item.icon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLessonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val isCurrent = (getCurrentLessonPosition(data) == position)
        viewHolder.bind(data[position], isCurrent)
    }

    override fun getItemCount() = data.size
}