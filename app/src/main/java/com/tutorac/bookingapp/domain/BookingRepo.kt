package com.tutorac.bookingapp.domain

import com.tutorac.bookingapp.data.Seat

interface BookingRepo {

    suspend fun getSeatMap() : MutableList<Seat>
    suspend fun updateSeatMao(seatMap: MutableList<Seat>)
    suspend fun deleteSeatMap()
}