package com.joesemper.timetable.data.datasource

import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.data.repository.ClassesRepository
import com.joesemper.timetable.util.*

class ClassesRepositoryMockImpl : ClassesRepository {

    private val currentClasses = getRandomClasses()

    override suspend fun getAllClasses(): List<Lesson> {
        return currentClasses
    }

    private fun getRandomClasses(): List<Lesson> {
        val result = mutableListOf<Lesson>()

        (0..SCHOOL_DAYS_IN_WEEK).forEach { numOfDay ->
            (0..getNumOfLessons()).forEach { numOfLesson ->
                val lesson = getRandomLesson()
                result.add(
                    Lesson(
                        name = lesson,
                        homework = "$lesson homework",
                        teacher = getRandomTeacher(),
                        startAt = getLessonStartTime(numOfLesson, numOfDay),
                        endAt = getLessonEndTime(numOfLesson, numOfDay),
                        isExtra = isLessonExtra()
                    )
                )
            }
        }
        return result
    }
}