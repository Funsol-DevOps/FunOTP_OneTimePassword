/**
 * Created by Osama Mumtaz
 * Website: https://www.osamamumtaz.com
 * Github: https://github.com/osama1malik
 * Copyright (c) 2022. All rights reserved.
 */
package com.scorpio.otpsdk.viewModels

import android.util.Log
import com.scorpio.otpsdk.model.OtpRequest
import com.scorpio.otpsdk.model.OtpResponse
import com.scorpio.otpsdk.retrofit.GetOtp
import com.scorpio.otpsdk.utils.Constants

object OTPRepository {
    suspend fun getOtpResponse(auth_token: String, email: String, requestServer: OtpRequest): OtpResponse? {
        val baseApiService = GetOtp.getOtp()
        return try {
            val otpResponse = baseApiService.requestAppAdData(auth_token, "${Constants.BASE_URL}otp", requestServer.subject, email, requestServer.upper, requestServer.lower)
            Log.i(Constants.TAG, "sendOtp: $otpResponse")
            otpResponse
        } catch (e: java.lang.Exception) {
            Log.i(Constants.TAG, "sendOtp: ", e)
            OtpResponse("failure", -1)
        } catch (e: Exception) {
            Log.i(Constants.TAG, "sendOtp: ", e)
            OtpResponse("failure", -1)
        }
    }
}