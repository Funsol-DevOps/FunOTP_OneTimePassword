/**
 * Created by Osama Mumtaz
 * Website: https://www.osamamumtaz.com
 * Github: https://github.com/osama1malik
 * Copyright (c) 2022. All rights reserved.
 */
package com.scorpio.otpsdk.retrofit

import com.scorpio.otpsdk.utils.Constants

object GetOtp {

    fun getOtp(): OtpRequestService {
        return RetrofitClient.getClient(Constants.BASE_URL).create(OtpRequestService::class.java)
    }

}