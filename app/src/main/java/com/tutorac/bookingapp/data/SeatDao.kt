package com.tutorac.bookingapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SeatDao {

    @Query("SELECT * FROM Seat")
    fun getSeatMap(): MutableList<Seat>

    @Insert(onConflict = REPLACE)
    fun setSeat(seat: Seat)

    @Transaction
    fun insertSeatMap(seatMap: List<Seat>) {
        seatMap.forEach {
            setSeat(it)
        }
    }

    @Query("DELETE FROM Seat")
    fun deleteSeatMap()
}
