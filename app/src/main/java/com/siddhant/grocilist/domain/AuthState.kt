package com.siddhant.grocilist.domain

import com.siddhant.grocilist.data.model.User

sealed class AuthState {
    object Idle: AuthState()
    object Loading: AuthState()
    class Success(val user: User): AuthState()
    class Error(val message:String):AuthState()
    class CodeSent(var verificationId:String): AuthState()


}