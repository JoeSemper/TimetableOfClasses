package com.joesemper.timetable.data.model

data class Lesson(
    val name: String = "",
    val homework: String = "",
    val teacher: String = "",
    val startAt: Long = 0,
    val endAt: Long = 0,
    val isExtra: Boolean = false,
    val icon: Int = 0
)