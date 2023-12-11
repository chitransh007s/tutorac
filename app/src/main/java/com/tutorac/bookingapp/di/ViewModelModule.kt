package com.tutorac.bookingapp.di

import androidx.lifecycle.ViewModel
import com.tutorac.bookingapp.ui.BookingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BookingViewModel::class)
    abstract fun provideBookingViewModel(viewModel: BookingViewModel): ViewModel

}