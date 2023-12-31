package com.tutorac.bookingapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    @get:Provides
    @get:Singleton val application: Application
)