package com.scorpio.otpsdk.retrofit

import com.scorpio.otpsdk.utils.Constants

object GetOtp {

    fun getOtp(): OtpRequestService {
        return RetrofitClient.getClient(Constants.BASE_URL).create(OtpRequestService::class.java)
    }

}