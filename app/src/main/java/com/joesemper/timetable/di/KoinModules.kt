package com.joesemper.timetable.di

import com.joesemper.timetable.data.datasource.ClassesRepositoryMockImpl
import com.joesemper.timetable.data.repository.ClassesRepository
import com.joesemper.timetable.ui.classes.ClassesFragment
import com.joesemper.timetable.ui.classes.ClassesViewModel
import com.joesemper.timetable.ui.home.HomeFragment
import com.joesemper.timetable.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<ClassesRepository> { ClassesRepositoryMockImpl() }
}

val homeModule = module {
    scope(named<HomeFragment>()) {
        viewModel { HomeViewModel(get()) }
    }
}

val classesModule = module {
    scope(named<ClassesFragment>()) {
        viewModel { ClassesViewModel(get()) }
    }
}