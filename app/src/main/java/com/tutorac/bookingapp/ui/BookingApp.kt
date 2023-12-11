package com.tutorac.bookingapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tutorac.bookingapp.R
import com.tutorac.bookingapp.domain.Family
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@Composable
fun AppBar(
    currentScreen: AppScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun BookingApp(
    viewModel: BookingViewModel,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreens.valueOf(
        backStackEntry?.destination?.route ?: AppScreens.BookTicket.name
    )

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    scope.launch {
        viewModel.showToast.consumeEach {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val family by viewModel.currentFamily.collectAsState()
        val seat by viewModel.seats.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreens.BookTicket.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppScreens.BookTicket.name) {
                viewModel.getSeatMap()
                viewModel.resetSelectedSeat()
                BookTicketScreen(
                    family = family,
                    onSubmit = {
                        viewModel.setFamily(it)
                        if (viewModel.isBookingValid()) {
                            navController.navigate(AppScreens.SelectSeats.name)
                        } else {
                            viewModel.showToast("Only ${viewModel.availableSeatCount()} seats available")
                        }
                    },
                    onToast = {
                        viewModel.showToast(it)
                    },
                    onDelete = {
                        viewModel.deleteTicket()
                    }
                )
            }
            composable(AppScreens.SelectSeats.name) {
                SelectTicketScreen(
                    seatMap = seat,
                    onSelectIndex = {
                        viewModel.setSelectedIndex(it)
                    },
                    onUnSelectIndex = {
                        viewModel.unSelectSeat(it)
                    },
                    onSubmitClick = {
                        if (viewModel.isSeatValid()) {
                            navController.navigate(AppScreens.CustomerDetails.name)
                        } else {
                            viewModel.showToast("Select seats for all persons")
                        }
                    },
                    onToast = {
                        viewModel.showToast(it)
                    }
                )
            }
            composable(AppScreens.CustomerDetails.name) {
                CustomerDetailScreen(
                    familySize = family?.size ?: 0,
                    onSubmitClick = {
                        if (viewModel.setFamilyDetail(it)) {
                            viewModel.bookTicket()
                            navController.navigate(AppScreens.BookingConfirmed.name)
                        } else {
                            viewModel.showToast("Enter Complete Detail")
                        }
                    }
                )
            }
            composable(AppScreens.BookingConfirmed.name) {
                BookingConfirmedScreen {
                    viewModel.setFamily(Family(0, ""))
                    viewModel.resetSelectedSeat()
                    navController.popBackStack(route = AppScreens.BookTicket.name, inclusive = false)
                }
            }
        }
    }
}