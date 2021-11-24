package com.joesemper.timetable.ui.classes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joesemper.timetable.R
import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.databinding.ItemClassesBinding
import com.joesemper.timetable.util.getLessonTime

class ClassesRvAdapter(
    private val data: List<Lesson>,
    private val currentPosition: Int,
    private val onClick: () -> Unit
) :
    RecyclerView.Adapter<ClassesRvAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemClassesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lesson, isCurrent: Boolean, onClick: () -> Unit) {
            with(binding) {
                tvLessonTitle.text = item.name
                tvTime.text = getLessonTime(item)
                tvTeacher.text = item.teacher
                ivLessonIcon.setImageResource(item.icon)

                if (isCurrent) {
                    videoLayout.visibility = View.VISIBLE
                    ivCurrent.visibility = View.VISIBLE
                    ivPoint.setImageResource(R.drawable.ic_point_white)
                    itemLesson.setOnClickListener { onClick() }
                } else {
                    videoLayout.visibility = View.GONE
                    ivCurrent.visibility = View.GONE
                    ivPoint.setImageResource(R.drawable.ic_point_green)
                    itemLesson.setOnClickListener {  }
                }

                if (item.isExtra) {
                    itemLessonInfo.setBackgroundResource(R.drawable.gradient_background)
                } else {
                    itemLessonInfo.setBackgroundResource(R.color.primary_light)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemClassesBinding.inflate(
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