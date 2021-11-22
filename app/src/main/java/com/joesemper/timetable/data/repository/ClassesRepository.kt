package com.joesemper.timetable.data.repository

import com.joesemper.timetable.data.model.Lesson

interface ClassesRepository {
    suspend fun getAllClasses(): List<Lesson>
}