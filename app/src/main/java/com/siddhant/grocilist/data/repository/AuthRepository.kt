package com.siddhant.grocilist.data.repository

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.siddhant.grocilist.data.model.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepository @Inject constructor() {
    suspend fun sendOtp(phoneNumber:String,activity: Activity):String{
        return suspendCancellableCoroutine { continuation ->
            val callbacks=object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onCodeSent(verificationId:String,token:PhoneAuthProvider.ForceResendingToken){
                    continuation.resume(verificationId)
                }
                override fun onVerificationFailed(exception: FirebaseException) {
                    continuation.resumeWithException(exception)
                }
                override fun onVerificationCompleted(credential: PhoneAuthCredential){


                }



            }
            val options= PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L,java.util.concurrent.TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            continuation.invokeOnCancellation {  }




        }

    }
    suspend fun verifyOtp(verificationId:String,OtpCode:String): User {
        val credential=PhoneAuthProvider.getCredential(verificationId,OtpCode)
        val authResult=FirebaseAuth.getInstance().signInWithCredential(credential).await()
        return User(
            uid=authResult.user?.uid?:"",
            phoneNumber = authResult.user?.phoneNumber?:""
        )

    }


}