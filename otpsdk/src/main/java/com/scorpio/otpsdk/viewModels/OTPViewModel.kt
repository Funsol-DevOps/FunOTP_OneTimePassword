/**
 * Created by Osama Mumtaz
 * Website: https://www.osamamumtaz.com
 * Github: https://github.com/osama1malik
 * Copyright (c) 2022. All rights reserved.
 */
package com.scorpio.otpsdk.viewModels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scorpio.otpsdk.model.OtpRequest
import com.scorpio.otpsdk.model.OtpResponse
import com.scorpio.otpsdk.utils.Constants
import kotlinx.coroutines.*

class OTPViewModel : ViewModel() {

    var resendTimer: Long = 60000 //SDK will allow to send OTP again after 1 minute.
    var expirationTime: Long = 600000 //SDK will expire the OTP after 10 minutes.

    private val _otpResendRemainingTime = MutableLiveData(resendTimer) //Get remaining resend time every second.
    val otpResendRemainingTime: LiveData<Long> = _otpResendRemainingTime

    private var otpResponse: OtpResponse? = null
    private var canResendOtp = true

    /**
     * Send Request to Server to request OTP.
     * @param auth_token: Authentication token provided by system administrator.
     * @param email: Email on which OTP will be send.
     * @param requestServer: OtpRequest.kt object which contains Subject, Upper Text, & Lower Text.
     * @param resultCallback: This function will return Boolean result weather OTP is sent or not.
     */
    fun sendOtp(
        auth_token: String,
        email: String,
        requestServer: OtpRequest = OtpRequest("", "", ""),
        resultCallback: (Boolean) -> Unit
    ) {
        if (canResendOtp) {

            if (auth_token.isEmpty()) {
                Log.i(Constants.TAG, "sendOtp: Authentication Token is missing")
                resultCallback(false)
                return
            }

            if (email.isEmpty()) {
                Log.i(Constants.TAG, "sendOtp: Email is missing")
                resultCallback(false)
                return
            }

            _otpResendRemainingTime.value = resendTimer
            viewModelScope.launch(Dispatchers.IO) {
                otpResponse = OTPRepository.getOtpResponse(auth_token, email, requestServer)
                if (otpResponse != null && otpResponse?.status != "failure") {
                    canResendOtp = false
                    withContext(Dispatchers.Main) {
                        resultCallback(true)
                        startResendTimer(resendTimer)
                        startExpirationTimer()
                    }
                } else {
                    canResendOtp = true
                    withContext(Dispatchers.Main) {
                        resultCallback(false)
                    }
                }
            }
        } else {
            resultCallback(false)
        }
    }

    /**
     * Verify the OTP by calling this method.
     * @param fromUser: OTP Enter by user to verify the email.
     */
    fun verifyOtp(fromUser: String): Boolean {
        otpResponse?.let {
            if (it.data.toString() == fromUser) {
                return true
            }
        }
        return false
    }

    /**
     * This function will start the countdown timer for expiration time.
     */
    private fun startExpirationTimer() {
        object : CountDownTimer(expirationTime, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                otpResponse = null
            }

        }.start()
    }

    /**
     * This function will start the timer to resend the OTP.
     */
    private fun startResendTimer(time: Long) {
        object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                _otpResendRemainingTime.postValue(p0)
            }

            override fun onFinish() {
                canResendOtp = true
            }

        }.start()
    }
}