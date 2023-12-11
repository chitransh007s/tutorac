package com.tutorac.bookingapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.isDigitsOnly
import com.tutorac.bookingapp.domain.FamilyDetail
import com.tutorac.bookingapp.ui.theme.Black
import com.tutorac.bookingapp.ui.theme.Grey
import com.tutorac.bookingapp.ui.theme.Transparent
import com.tutorac.bookingapp.ui.theme.White

@Composable
fun CustomerDetailScreen(
    modifier: Modifier = Modifier,
    familySize: Int,
    onSubmitClick: (MutableList<FamilyDetail>) -> Unit = {}
) {
    val familyList = MutableList(familySize) { FamilyDetail("", "") }

    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 13.dp)
            .fillMaxSize()
    ) {
       val (list, btnSubmit) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(list) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(btnSubmit.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            for (i in 0 until familySize) {
                CustomerDetailItem(
                    index = i,
                    onNameChange = {
                        familyList[i].name = it
                    },
                    onPhoneChange = {
                        familyList[i].phone = it
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .constrainAs(btnSubmit) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }
                .padding(vertical = 13.dp),
            onClick = {
                onSubmitClick(familyList)
            }
        ) {
            Text(text = "Confirm Booking")
        }
    }

}

@Composable
fun CustomerDetailItem(
    modifier: Modifier = Modifier,
    index: Int,
    onNameChange: (String) -> Unit = {},
    onPhoneChange: (String) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .padding(top = 13.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (tvTitle, tvName, tvPhone) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(tvTitle){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            text = "Member ${index.plus(1)}"
        )

        var nameText by remember { mutableStateOf("") }
        var phoneText by remember { mutableStateOf("") }

        TextField(
            value = nameText,
            onValueChange = {
                nameText = it
                onNameChange(nameText)
            },
            modifier = Modifier
                .constrainAs(tvName) {
                    top.linkTo(tvTitle.bottom)
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            textStyle = TextStyle(
                fontSize = 14.sp
            ),
            placeholder = { Text(text = "Enter Name") },
            shape = RoundedCornerShape(2.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                cursorColor = Black,
                textColor = Black,
                placeholderColor = Grey,
                focusedIndicatorColor = Transparent
            )
        )

        TextField(
            value = phoneText,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    phoneText = it.take(10)
                    onPhoneChange(phoneText)
                }
            },
            modifier = Modifier
                .constrainAs(tvPhone) {
                    top.linkTo(tvName.bottom)
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
            placeholder = { Text(text = "Enter Phone") },
            shape = RoundedCornerShape(2.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                cursorColor = Black,
                textColor = Black,
                placeholderColor = Grey,
                focusedIndicatorColor = Transparent
            )
        )
    }
}