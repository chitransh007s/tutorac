package com.tutorac.bookingapp.di

import android.app.Application
import com.tutorac.bookingapp.data.BookingDb
import com.tutorac.bookingapp.data.BookingRepository
import com.tutorac.bookingapp.domain.BookingRepo
import com.tutorac.bookingapp.domain.BookingUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesBookingUseCase(
        bookingRepo: BookingRepo
    ): BookingUseCase {
        return BookingUseCase(bookingRepo)
    }

    @Provides
    @Singleton
    fun providesBookingRepository(
        bookingDb: BookingDb
    ): BookingRepo {
        return BookingRepository(bookingDb)
    }

    @Provides
    @Singleton
    fun providesBookingDb(
        context: Application
    ): BookingDb {
        return BookingDb.invoke(context)
    }
}