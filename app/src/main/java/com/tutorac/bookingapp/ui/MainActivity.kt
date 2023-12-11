package com.tutorac.bookingapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tutorac.bookingapp.App
import com.tutorac.bookingapp.ui.theme.BookingAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: BookingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContent {
            BookingAppTheme {
                BookingApp(viewModel)
            }
        }
    }
}