package com.joesemper.timetable.ui.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.data.repository.ClassesRepository
import kotlinx.coroutines.launch

class ClassesViewModel(private val classesRepository: ClassesRepository) : ViewModel() {
    var currentClasses = listOf<Lesson>()

    init {
        viewModelScope.launch {
            currentClasses = classesRepository.getAllClasses()
        }
    }
}