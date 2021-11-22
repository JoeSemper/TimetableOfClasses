package com.joesemper.timetable.data.datasource

import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.data.repository.ClassesRepository

class ClassesRepositoryMockImpl: ClassesRepository {
    override suspend fun getAllClasses(): List<Lesson> {
        return emptyList()
    }
}