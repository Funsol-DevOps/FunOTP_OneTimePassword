/**
 * Created by Osama Mumtaz
 * Website: https://www.osamamumtaz.com
 * Github: https://github.com/osama1malik
 * Copyright (c) 2022. All rights reserved.
 */
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
