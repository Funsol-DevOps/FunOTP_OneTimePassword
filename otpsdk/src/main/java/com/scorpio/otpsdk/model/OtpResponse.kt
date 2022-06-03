package com.scorpio.otpsdk.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class OtpResponse(
    val status: String,
    val data: Int
) : Parcelable
