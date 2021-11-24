package com.joesemper.timetable.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.databinding.ItemLessonBinding
import com.joesemper.timetable.util.getLessonTime

class HomeLessonsRvAdapter(
    private val data: List<Lesson>,
    private val currentPosition: Int,
    private val onClick: () -> Unit
) :
    RecyclerView.Adapter<HomeLessonsRvAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lesson, isCurrent: Boolean, onClick: () -> Unit) {
            with(binding) {
                tvLessonTitle.text = item.name
                tvLessonTime.text = getLessonTime(item)
                ivLessonIcon.setImageResource(item.icon)
                if (isCurrent) {
                    itemLesson.setOnClickListener { onClick() }
                    videoLayout.visibility = View.VISIBLE
                } else {
                    itemLesson.setOnClickListener {  }
                    videoLayout.visibility = View.GONE
                }

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
        val isCurrent = (data[position] == data[currentPosition])
        viewHolder.bind(data[position], isCurrent, onClick)
    }

    override fun getItemCount() = data.size
}