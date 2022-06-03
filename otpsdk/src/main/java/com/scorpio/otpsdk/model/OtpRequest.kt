/**
 * Created by Osama Mumtaz
 * Website: https://www.osamamumtaz.com
 * Github: https://github.com/osama1malik
 * Copyright (c) 2022. All rights reserved.
 */
package com.scorpio.otpsdk.model

/**
 * Class to make request to server
 * @param subject: subject on the email which will be sent for OTP.
 * @param upper: text to shown on email before OTP.
 * @param lower: text to shown on email after OTP.
 */
data class OtpRequest(
    val subject: String,
    val upper: String,
    val lower: String
)