package com.fwahyudianto.militant.data.model

//  Import Library
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 * https://www.fwahyudianto.id
 * ® Wahyudianto, Fajar
 * Email 	: me@fwahyudianto.id
 *
 * 	Date			User				Note
 *  -------------------------------------------------------------------------------------------------------------------------
 *  End Revised
 */

@Parcelize
// Event constructor
data class Event(
    val strEventImage: String,
    val strEventCategory: String,
    val strEventName: String,
    val strEventSummary: String,
) : Parcelable