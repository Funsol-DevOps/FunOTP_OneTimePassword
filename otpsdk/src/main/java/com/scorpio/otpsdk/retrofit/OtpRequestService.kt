/**
 * Created by Osama Mumtaz
 * Website: https://www.osamamumtaz.com
 * Github: https://github.com/osama1malik
 * Copyright (c) 2022. All rights reserved.
 */
package com.scorpio.otpsdk.retrofit

import com.scorpio.otpsdk.model.OtpResponse
import retrofit2.http.*

interface OtpRequestService {
    @POST
    @FormUrlEncoded
    suspend fun requestAppAdData(
        @Header("Authorization") authorization: String,
        @Url url: String,
        @Field("subject") subject: String,
        @Field("email") email: String,
        @Field("upper") upper: String,
        @Field("lower") lower: String
    ): OtpResponse?
}