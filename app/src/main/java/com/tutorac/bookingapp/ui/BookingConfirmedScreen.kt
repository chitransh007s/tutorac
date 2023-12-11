package com.tutorac.bookingapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun BookingConfirmedScreen(
    onReset: () -> Unit = {}
) {

    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        //Mocking Some Processing Behaviour
        delay(500)
        message = "Booking Confirmed"
        delay(1500)
        onReset()
    }

    Box(
        modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            targetState = message,
            transitionSpec = {
                scaleIn(animationSpec = tween(durationMillis = 500)) togetherWith ExitTransition.None
            },
            contentAlignment = Alignment.Center
        ) { targetMessage ->
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "$targetMessage",
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}