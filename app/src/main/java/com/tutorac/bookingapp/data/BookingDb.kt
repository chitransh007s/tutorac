package com.tutorac.bookingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Seat::class,],
    version = 1,
    exportSchema = true,
)
abstract class BookingDb : RoomDatabase() {
    abstract fun seatDao(): SeatDao

    companion object {
        @Volatile
        private var instance: BookingDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, BookingDb::class.java, "booking.db"
        ).fallbackToDestructiveMigration().build()
    }
}