package com.qaizen.admin.bookings.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.qaizen.admin.bookings.presentation.dialogs.ApproveBookingDialog
import com.qaizen.admin.bookings.presentation.dialogs.DeclineBookingDialog

@Composable
fun BookingsPage(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
    bookingsViewModel: BookingsViewModel,
) {

    val context = LocalContext.current
    val uiState by bookingsViewModel.uiState.collectAsState()

    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    val bookingsList = bookingsViewModel.bookings.collectAsState().value
    var showApproveDialog by remember { mutableStateOf(false) }
    var showDeclineDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
    ) {
        if (bookingsList == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (bookingsList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "4😶4", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "No bookings found")
            }
        } else

            LazyVerticalStaggeredGrid(
                modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
            ) {
                items(bookingsList) { bookingItem ->

                    BookingListItem(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        bookingItem = bookingItem,
                        onClickApprove = {
                            bookingsViewModel.updateCurrentBooking(bookingData = bookingItem)
                            showApproveDialog = true
                        },
                        onClickDecline = {
                            bookingsViewModel.updateCurrentBooking(bookingData = bookingItem)
                            showDeclineDialog = true
                        }
                    )
                }
            }

        if (showApproveDialog) {
            ApproveBookingDialog(
                onApprove = {
                    bookingsViewModel.approvePayment(
                        bookingData = uiState.currentBooking!!,
                        onSuccess = {
                            Toast.makeText(context, "Booking approved", Toast.LENGTH_SHORT).show()
                        },
                        onError = { exception ->
                            Toast.makeText(
                                context,
                                "An error occurred: ${exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                    showApproveDialog = false
                },
                onDismiss = {
                    showApproveDialog = false
                }
            )
        }

        if (showDeclineDialog) {
            DeclineBookingDialog(
                onApprove = {
                    bookingsViewModel.declineBooking(
                        bookingId = uiState.currentBooking?.timeStamp!!,
                        fcmTokens = uiState.currentBooking?.userFcmTokens as List<String>,
                        notificationsOn = uiState.currentBooking?.notificationsOn!!,
                        onSuccess = {
                            Toast.makeText(context, "Booking declined", Toast.LENGTH_SHORT).show()
                        },
                        onError = { exception ->
                            Toast.makeText(
                                context,
                                "An error occurred: ${exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                    showDeclineDialog = false
                },
                onDismiss = {
                    showDeclineDialog = false
                }
            )
        }
    }
}