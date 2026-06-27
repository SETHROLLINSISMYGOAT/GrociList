package com.siddhant.grocilist.ui.auth

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.siddhant.grocilist.domain.AuthState

@Composable
fun PhoneInputScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val authState by viewModel.authState.collectAsState()
    val activity = LocalContext.current as Activity
    var phoneNumber by remember { mutableStateOf("") }
    Column {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it }
        )
        Button(onClick = {
            viewModel.sendOtp(phoneNumber, activity)
        }) {
            Text("Send OTP")
        }
        when(authState){
            is AuthState.Idle->{}
            is AuthState.Loading->{
                CircularProgressIndicator()
            }
            is AuthState.Success->{}
            is AuthState.Error->{
                Text((authState as AuthState.Error).message)
            }
            is AuthState.CodeSent->{}

        }

    }





}