package com.tutorac.bookingapp.ui

import androidx.annotation.StringRes
import com.tutorac.bookingapp.R

enum class AppScreens(@StringRes val title: Int) {
    BookTicket(title = R.string.screen_1),
    SelectSeats(title = R.string.screen_2),
    CustomerDetails(title = R.string.screen_3),
    BookingConfirmed(title = R.string.screen_4)
}