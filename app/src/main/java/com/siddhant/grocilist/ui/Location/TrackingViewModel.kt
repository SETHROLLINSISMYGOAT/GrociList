package com.siddhant.grocilist.ui.Location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.google.android.gms.maps.model.LatLng

import com.siddhant.grocilist.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val locationRepository: LocationRepository

): ViewModel(){
    private val _location = MutableStateFlow<LatLng?>(null)
    val location : StateFlow<LatLng?> = _location
    fun startTracking(orderId:String){

            viewModelScope.launch {
                locationRepository.getDeliveryLocation(orderId).collect {latLng ->
                    _location.value = latLng
                }
            }

    }
    fun updateLocation(orderId:String,lat:Double,lng:Double){
        viewModelScope.launch {
            locationRepository.updateLocation(orderId,lat,lng)
        }
    }
}
