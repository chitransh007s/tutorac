package com.tutorac.bookingapp

import android.app.Application
import com.tutorac.bookingapp.di.AppComponent
import com.tutorac.bookingapp.di.AppModule
import com.tutorac.bookingapp.di.DaggerAppComponent
import com.tutorac.bookingapp.di.DataModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .build()
    }
}