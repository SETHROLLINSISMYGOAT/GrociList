package com.siddhant.grocilist.ui.Coupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddhant.grocilist.data.model.Coupon
import com.siddhant.grocilist.data.repository.CouponRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CouponViewModel @Inject constructor(
    private val repository: CouponRepository

): ViewModel() {
    private val _couponState = MutableStateFlow<Coupon?>(null)
    val couponState: StateFlow<Coupon?> = _couponState
    fun applyCoupon(code: String){
        viewModelScope.launch {
            val coupon = repository.validateCoupon(code)
            _couponState.value=coupon

        }
    }


}