package com.tutorac.bookingapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.isDigitsOnly
import com.tutorac.bookingapp.domain.Family
import com.tutorac.bookingapp.ui.theme.Black
import com.tutorac.bookingapp.ui.theme.Grey
import com.tutorac.bookingapp.ui.theme.Transparent
import com.tutorac.bookingapp.ui.theme.White


@Composable
@Preview
fun BookTicketScreen(
    modifier: Modifier = Modifier,
    family: Family? = null,
    onSubmit: (Family) -> Unit = {},
    onToast: (String) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 13.dp)
            .fillMaxSize()
    ) {
        val (tvSize, tvName, btnSubmit, btnDelete) = createRefs()

        var sizeText by remember { mutableStateOf((family?.size ?: "").toString()) }
        var nameText by remember { mutableStateOf(family?.name.orEmpty()) }

        TextField(
            value = sizeText,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    sizeText = it
                }
            },
            modifier = Modifier
                .constrainAs(tvSize) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
                .padding(top = 13.dp)
                .border(
                    BorderStroke(1.dp, Black),
                    shape = RoundedCornerShape(2.dp)
                ),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            placeholder = { Text(text = "Enter no. of Person") },
            shape = RoundedCornerShape(2.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                cursorColor = Black,
                textColor = Black,
                placeholderColor = Grey,
                focusedIndicatorColor = Transparent
            )
        )

        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = nameText,
            onValueChange = { nameText = it },
            modifier = Modifier
                .constrainAs(tvName) {
                    top.linkTo(tvSize.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
                .padding(top = 13.dp)
                .border(
                    BorderStroke(1.dp, Black),
                    shape = RoundedCornerShape(2.dp)
                ),
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide()}
            ),
            placeholder = { Text(text = "Enter Family Name") },
            shape = RoundedCornerShape(2.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                cursorColor = Black,
                textColor = Black,
                placeholderColor = Grey,
                focusedIndicatorColor = Transparent
            )
        )
        
        Button(
            modifier = Modifier
                .constrainAs(btnSubmit) {
                    bottom.linkTo(btnDelete.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
                .padding(bottom = 13.dp),
            onClick = {
                if (sizeText.isNotBlank() && (1..10).contains(sizeText.toInt())) {
                    onSubmit(
                        Family(
                            size = sizeText.toIntOrNull() ?: 0,
                            name = nameText
                        )
                    )
                } else {
                    onToast("No. of Person should be between 1-10")
                }
            }
        
        ) {
            Text(text = "Book Ticket")
        }

        Button(
            modifier = Modifier
                .constrainAs(btnDelete) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
                .padding(bottom = 13.dp),
            onClick = { onDelete() }

        ) {
            Text(text = "Delete All Tickets")
        }
    }
}