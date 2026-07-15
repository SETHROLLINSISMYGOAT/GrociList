package com.siddhant.grocilist.ui.Location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun OrderTrackingScreen(
    orderId: String,
    navController: NavController,
    viewModel: TrackingViewModel = hiltViewModel()
) {
    val location by viewModel.location.collectAsState()

    LaunchedEffect(orderId) {
        viewModel.startTracking(orderId)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            location ?: LatLng(28.6139, 77.2090),
            15f
        )
    }

    LaunchedEffect(location) {
        location?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Tracking your order: $orderId")
        Button(onClick = {
            viewModel.updateLocation(
                orderId = orderId,
                lat = 28.6139,
                lng = 77.2090
            )
        }) {
            Text("Update Location")
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            location?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Delivery Partner"
                )
            }
        }
    }

}