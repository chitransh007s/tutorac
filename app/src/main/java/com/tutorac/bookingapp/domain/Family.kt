package com.tutorac.bookingapp.domain

data class Family(val size: Int, val name: String) {

    fun isAllTicketSelected(count: Int) = size == count

    fun isTicketAvailable(availableSeat: Int) = size <= availableSeat
}

data class FamilyDetail(var name: String, var phone: String)
