package com.tutorac.bookingapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tutorac.bookingapp.data.Seat
import com.tutorac.bookingapp.data.SeatState
import com.tutorac.bookingapp.ui.theme.Black
import com.tutorac.bookingapp.ui.theme.Green
import com.tutorac.bookingapp.ui.theme.Grey
import com.tutorac.bookingapp.ui.theme.White

@Composable
fun SelectTicketScreen(
    modifier: Modifier = Modifier,
    seatMap: MutableList<Seat> = mutableListOf(),
    onSelectIndex: (Int) -> Unit = {},
    onUnSelectIndex: (Int) -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onToast: (String) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ){
        val (lazyGrid, btnSubmit) = createRefs()

        LazyVerticalGrid(
            modifier = Modifier
                .constrainAs(lazyGrid) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(btnSubmit.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            content = {
                itemsIndexed(
                    items = seatMap,
                    key = { _, item -> "${item.seatRowId}:${item.seatNum}" }
                ) { index, item, ->
                    Box(
                        modifier = Modifier
                            .height(55.dp)
                            .border(0.5.dp, Black, RoundedCornerShape(2.dp))
                            .background(
                                color = when (item.state) {
                                    SeatState.Available -> White
                                    SeatState.Selected -> Green
                                    SeatState.Occupied,
                                    SeatState.Reserved -> Grey
                                },
                                shape = RoundedCornerShape(2.dp)
                            )
                            .clickable {
                                when (item.state) {
                                    SeatState.Available -> onSelectIndex(index)
                                    SeatState.Selected -> onUnSelectIndex(index)
                                    SeatState.Occupied,
                                    SeatState.Reserved -> onToast("Seat Unavailable")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Row-${item.seatRowId.plus(1)}\nSeat-${item.seatNum.plus(1)}",
                            color = Black
                        )
                    }
                }
            })

        Button(
            modifier = Modifier
                .constrainAs(btnSubmit) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
                .padding(horizontal = 13.dp)
                .padding(bottom = 13.dp),
            onClick = {
                onSubmitClick()
            }
        ) {
            Text(text = "Select Seat")
        }

    }


}