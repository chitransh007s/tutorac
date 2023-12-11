package com.tutorac.bookingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorac.bookingapp.data.Seat
import com.tutorac.bookingapp.data.SeatState
import com.tutorac.bookingapp.domain.BookingUseCase
import com.tutorac.bookingapp.domain.Family
import com.tutorac.bookingapp.domain.FamilyDetail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookingViewModel @Inject constructor(private val useCase: BookingUseCase): ViewModel() {

    private val _currentFamily: MutableStateFlow<Family?> = MutableStateFlow(null)
    val currentFamily: StateFlow<Family?> = _currentFamily.asStateFlow()

    private val _seats: MutableStateFlow<MutableList<Seat>> = MutableStateFlow(mutableListOf())
    val seats: StateFlow<MutableList<Seat>> = _seats.asStateFlow()

    private val _familyDetail: MutableStateFlow<MutableList<FamilyDetail>> = MutableStateFlow(mutableListOf())

    private val _showToast = Channel<String>()
    val showToast: Channel<String> = _showToast

    fun getSeatMap() {
        viewModelScope.launch {
            _seats.update {
                useCase.getSeatMap()
            }
        }
    }

    fun setFamily(family: Family) {
        _currentFamily.update {
            it?.copy(
                size = family.size,
                name = family.name
            ) ?: (family)
        }
    }

    fun setSelectedIndex(index: Int) {
        viewModelScope.launch {
            _seats.update {
                useCase.setSelectedSeat(
                    seatMap = seats.value,
                    requiredSeat = currentFamily.value?.size ?: 0,
                    selectedIndex = index
                )
            }
        }
    }

    fun unSelectSeat(index: Int) {
        _seats.update {
            useCase.unSelectSeat(
                seats.value,
                index
            )
        }
    }

    fun isSeatValid() = currentFamily.value?.isAllTicketSelected(useCase.getSelectedSeatCount()) == true

    fun isBookingValid() = currentFamily.value?.isTicketAvailable(availableSeatCount()) == true

    fun availableSeatCount() = seats.value.count { it.state == SeatState.Available }

    fun resetSelectedSeat() {
        useCase.setSelectedSeatCount(0)
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _showToast.send(message)
        }
    }

    fun setFamilyDetail(familyList: MutableList<FamilyDetail>) : Boolean {
        return if (useCase.checkFamilyDetail(familyList)) {
            _familyDetail.update {
                familyList
            }
            true
        } else {
            false
        }
    }

    fun bookTicket() {
        val updatedList = seats.value.map { seat ->
            if (seat.state == SeatState.Selected) {
                seat.copy(
                    state = SeatState.Occupied
                )
            } else {
                seat
            }
        }.toMutableList()
        _seats.update { updatedList }
        viewModelScope.launch {
            useCase.bookTicket(updatedList)
        }
    }

    fun deleteTicket() {
        viewModelScope.launch {
            useCase.deleteTicket()
        }
    }

}