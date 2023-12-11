package com.tutorac.bookingapp.domain

import com.tutorac.bookingapp.data.Seat
import com.tutorac.bookingapp.data.SeatState

class BookingUseCase(private val bookingRepo: BookingRepo) {

    private var selectedSeat: Int = 0

    suspend fun getSeatMap() : MutableList<Seat> {
        return bookingRepo.getSeatMap()
    }

    fun getSelectedSeatCount() = selectedSeat
    fun setSelectedSeatCount(count: Int) {
        selectedSeat = count
    }

    suspend fun setSelectedSeat(
        seatMap: MutableList<Seat>,
        requiredSeat: Int,
        selectedIndex: Int
    ): MutableList<Seat> {
        var updatedList = seatMap.map { it.copy() }.toMutableList()

        /**
         * Checking if user wants to change :-
         * 1. Whole selected seats
         * 2. Only the unselected seats
         */
        if (selectedSeat == requiredSeat) {
            selectedSeat = 0
            updatedList = getSeatMap()
        }

        var currentIndex = selectedIndex
        while (selectedSeat < requiredSeat) {

            /**
             * After the last index, start from initial index till :-
             * 1. requiredSeat reaches 0
             * 2. traversed the loop exactly once
             */

            if (currentIndex > updatedList.lastIndex) {
                currentIndex = 0
            }

            if (updatedList[currentIndex].state == SeatState.Available) {
                updatedList[currentIndex] = updatedList[currentIndex].copy(
                    state = SeatState.Selected
                )
                selectedSeat++
            }

            currentIndex++

            //traverse the loop only once
            if (currentIndex == selectedIndex) {
                break
            }
        }

        return updatedList
    }

    fun unSelectSeat(
        seatMap: List<Seat>,
        unSelectIndex: Int
    ): MutableList<Seat> {
        val updatedList = seatMap.map { it.copy() }.toMutableList()
        if (updatedList[unSelectIndex].state == SeatState.Selected) {
            updatedList[unSelectIndex].state = SeatState.Available
            selectedSeat--
        }
        return updatedList
    }

    fun checkFamilyDetail(familyList: MutableList<FamilyDetail>) : Boolean {
        var isValid = true
        familyList.forEach{
            if (it.name.isBlank() || it.phone.isBlank()) {
                isValid = false
                return@forEach
            }
        }

        return isValid
    }

    suspend fun bookTicket(seatMap: MutableList<Seat>) {
        bookingRepo.updateSeatMao(seatMap)
    }

    suspend fun deleteTicket() {
        bookingRepo.deleteSeatMap()
    }
}