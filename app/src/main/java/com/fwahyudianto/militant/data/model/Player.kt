package com.fwahyudianto.militant.data.model

//  Import Library
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 */

@Parcelize
// Player constructor
data class Player(
    val iNoTshirt: Int,
    val iPlayerPhoto: Int,
    val strPlayerName: String,
    val strPlayerFullName: String,
    val strPlayerDescription: String,
) : Parcelable