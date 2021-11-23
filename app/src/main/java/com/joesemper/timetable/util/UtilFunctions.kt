package com.joesemper.timetable.util

import com.joesemper.timetable.R
import com.joesemper.timetable.data.model.Lesson
import java.text.SimpleDateFormat
import java.util.*

fun getNumOfLessons(): Int {
    return (4..7).random()
}

fun getRandomLesson(): String {
    return LIST_OF_CLASSES.random()
}

fun getRandomTeacher(): String {
    return LIST_OF_TEACHERS.random()
}

fun isLessonExtra(): Boolean {
    return (0..10).random() > 7
}

fun getRandomImage(): Int {
    return when((0..2).random()) {
        0 -> R.drawable.ic_ball
        1 -> R.drawable.ic_scince
        else -> R.drawable.ic_book
    }
}

fun getLessonStartTime(numOfLesson: Int, numOfDay: Int): Long {
    val calendar = Calendar.getInstance()
//    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    val date = calendar.timeInMillis
    return (date + numOfDay * MILLISECONDS_IN_DAY + 8 * MILLISECONDS_IN_HOUR + numOfLesson * MILLISECONDS_IN_HOUR)
}

fun getLessonEndTime(numOfLesson: Int, numOfDay: Int): Long {
    return getLessonStartTime(numOfLesson, numOfDay) + (45 * MILLISECONDS_IN_MINUTE)
}

fun getHoursByMilliseconds(ms: Long): String {
    val sdf = SimpleDateFormat("HH", Locale.US)
    val date = Date(ms)
    return sdf.format(date)
}

fun getDateByMilliseconds(ms: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    val date = Date(ms)
    return sdf.format(date)
}

fun getDateByMillisecondsTextMonth(ms: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.US)
    val date = Date(ms)
    return sdf.format(date)
}

fun getDayByMilliseconds(ms: Long): String {
    val sdf = SimpleDateFormat("dd", Locale.US)
    val date = Date(ms)
    return sdf.format(date)
}

fun getTimeByMilliseconds(ms: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.US)
    val date = Date(ms)
    return sdf.format(date)
}

fun getDateAndTimeByMilliseconds(ms: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yy HH:mm", Locale.US)
    val date = Date(ms)
    return sdf.format(date)
}

fun getRemainTime(target: Long): Long {
    return target - Date().time
}

fun getRemainDays(target: Long): String {
    val result = (target / MILLISECONDS_IN_DAY).toInt()
    return if (result > 9) result.toString() else "0$result"
}

fun getRemainHours(target: Long): String {
    val result = ((target % MILLISECONDS_IN_DAY) / MILLISECONDS_IN_HOUR).toInt()
    return if (result > 9) result.toString() else "0$result"
}

fun getRemainMinutes(target: Long): String {
    val result =
        (((target % MILLISECONDS_IN_DAY) % MILLISECONDS_IN_HOUR) / MILLISECONDS_IN_MINUTE).toInt()
    return if (result > 9) result.toString() else "0$result"
}

fun getRemainSeconds(target: Long): Int {
    return ((((target % MILLISECONDS_IN_DAY) % MILLISECONDS_IN_HOUR) % MILLISECONDS_IN_MINUTE) / 1000).toInt()
}

fun getLessonTime(lesson: Lesson): String {
    return "${getTimeByMilliseconds(lesson.startAt)} - ${getTimeByMilliseconds(lesson.endAt)}"
}

fun isItCurrentLesson(lesson: Lesson): Boolean {
    return ((lesson.startAt - MILLISECONDS_IN_MINUTE * 15) < Date().time) && (lesson.endAt > Date().time)
}

fun currentLessonPosition(lessons: List<Lesson>): Int {
    lessons.forEachIndexed { index, lesson ->
        if (isItCurrentLesson(lesson)) return index
    }
    return 0
}

fun getTodayLessons(lessons: List<Lesson>): List<Lesson>{
    return lessons.filter {
        it.startAt/MILLISECONDS_IN_DAY == Date().time/ MILLISECONDS_IN_DAY
    }
}




