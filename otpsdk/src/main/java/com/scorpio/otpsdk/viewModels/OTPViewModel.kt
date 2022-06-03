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

    var resendTimer: Long = 60000
    var expirationTime: Long = 600000

    private val _otpResendRemainingTime = MutableLiveData<Long>(resendTimer)
    val otpResendRemainingTime: LiveData<Long> = _otpResendRemainingTime

    private var otpResponse: OtpResponse? = null
    private var canResendOtp = true

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

    fun verifyOtp(fromUser: String): Boolean {
        otpResponse?.let {
            if (it.data.toString() == fromUser) {
                return true
            }
        }
        return false
    }

    private fun startExpirationTimer() {
        object : CountDownTimer(expirationTime, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                otpResponse = null
            }

        }.start()
    }

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