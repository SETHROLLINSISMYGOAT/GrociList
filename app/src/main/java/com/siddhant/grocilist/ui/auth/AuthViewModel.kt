package com.siddhant.grocilist.ui.auth

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddhant.grocilist.data.repository.AuthRepository
import com.siddhant.grocilist.domain.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _authState= MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> =_authState
    fun sendOtp(phoneNumber: String,activity: Activity){
        viewModelScope.launch{
            _authState.value= AuthState.Loading

            try{
                val verificationId=authRepository.sendOtp(phoneNumber,activity)
                _authState.value=AuthState.CodeSent(verificationId)
            }catch (e: Exception){
                _authState.value=AuthState.Error(e.message?:"Something went wrong")
            }



        }
    }
    fun verifyOtp(verificationId:String,otpCode:String){
        viewModelScope.launch{
            _authState.value= AuthState.Loading
            try{
                val user=authRepository.verifyOtp(verificationId,otpCode)
                _authState.value= AuthState.Success(user)
            }catch(e: Exception){
                _authState.value= AuthState.Error(e.message?:"Otp Mismatch")
            }
        }
    }


}



