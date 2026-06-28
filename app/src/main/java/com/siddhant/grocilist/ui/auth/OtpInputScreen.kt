package com.siddhant.grocilist.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.siddhant.grocilist.domain.AuthState

@Composable
fun OtpInputScreen(verificationId:String,navController: NavController,viewModel: AuthViewModel= hiltViewModel()) {
    var otpCode by remember{
        mutableStateOf("")

    }
    val authState by viewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        when(authState){
            is AuthState.Success->{
                navController.navigate("home")
            }
            else->{

            }

        }


    }
    Column{
        TextField(
            value=otpCode,
            onValueChange={
                otpCode=it
            }
        )
        Button(onClick = {
            viewModel.verifyOtp(verificationId,otpCode)
        }){
            Text("Verify OTP")

        }
        when (authState) {
            is AuthState.Idle -> {}
            is AuthState.Loading -> { CircularProgressIndicator() }
            is AuthState.Success -> {}
            is AuthState.Error -> { Text((authState as AuthState.Error).message) }
            is AuthState.CodeSent -> {}
        }


    }
}