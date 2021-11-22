package com.joesemper.timetable.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.timetable.data.model.Lesson
import com.joesemper.timetable.data.repository.ClassesRepository
import com.joesemper.timetable.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timer

class HomeViewModel(private val classesRepository: ClassesRepository) : ViewModel() {

    var currentClasses = listOf<Lesson>()
    val remainToExamState: MutableStateFlow<String> = MutableStateFlow("0")

    init {
        viewModelScope.launch {
            currentClasses = classesRepository.getAllClasses()
        }
        getRemainTimeToExam()
    }

    private fun getRemainTimeToExam() {
        timer(
            name = "",
            daemon = false,
            startAt = Date(),
            period = 1000,
        ) {
            val exam = currentClasses.last().endAt + MILLISECONDS_IN_DAY * 10
            val dif = getRemainTime(exam)
            remainToExamState.value =
                "${getRemainDays(dif)} ${getRemainHours(dif)} ${getRemainMinutes(dif)}"

        }
    }

}