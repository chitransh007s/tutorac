package com.tutorac.bookingapp.data

import com.tutorac.bookingapp.domain.BookingRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookingRepository(val bookingDb: BookingDb) : BookingRepo {

    override suspend fun getSeatMap(): MutableList<Seat> {
        return withContext(Dispatchers.IO) {
            //Mocking Seat Map Data Locally for 5*5 seats
            val seatMap = bookingDb.seatDao().getSeatMap()
            if (seatMap.isEmpty()) {
                for (i in 0..4) {
                    for (j in 0..4) {
                        seatMap.add(Seat(0, i, j, SeatState.Available))
                    }
                }
                bookingDb.seatDao().insertSeatMap(seatMap)
            }

            return@withContext withContext(Dispatchers.Main) { seatMap }
        }
    }

    override suspend fun updateSeatMao(seatMap: MutableList<Seat>) {
        withContext(Dispatchers.IO) {
            bookingDb.seatDao().insertSeatMap(seatMap)
        }
    }

    override suspend fun deleteSeatMap() {
        withContext(Dispatchers.IO) {
            bookingDb.seatDao().deleteSeatMap()
        }
    }

}