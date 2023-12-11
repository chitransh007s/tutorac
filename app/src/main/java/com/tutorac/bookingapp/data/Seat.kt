package com.tutorac.bookingapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(
        value = ["seatRowId", "seatNum"],
        unique = true
    )]
)
data class Seat(
    @PrimaryKey(autoGenerate = true) val index: Int,
    @ColumnInfo(name = "seatRowId") val seatRowId: Int,
    @ColumnInfo(name = "seatNum") val seatNum: Int,
    @ColumnInfo(name = "state") var state: SeatState
)

enum class SeatState { Available, Occupied, Selected, Reserved }