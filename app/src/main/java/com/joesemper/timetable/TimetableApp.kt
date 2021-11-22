package com.joesemper.timetable

import android.app.Application
import com.joesemper.timetable.di.appModule
import com.joesemper.timetable.di.classesModule
import com.joesemper.timetable.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TimetableApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TimetableApp)
            modules(
                listOf(
                    appModule,
                    homeModule,
                    classesModule
                )
            )
        }
    }
}