package com.fwahyudianto.militant.data.services

import com.fwahyudianto.militant.data.response.DetailResponse
import com.fwahyudianto.militant.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  This software, all associated documentation, and all copies are CONFIDENTIAL INFORMATION of Kalpawreksa Teknologi Indonesia
 *  https://www.fwahyudianto.id
 *  ® Wahyudianto, Fajar
 *  Email 	: me@fwahyudianto.id
 */

interface EventService {
    //  Get All Events
    @GET("events")
    fun getList(): Call<EventResponse>

    @GET("events")
    fun getListByParam(
        @Query("active") active: Int? = null,
        @Query("q") keywords: String? = null,
        @Query("limit") limit: Int? = 40
    ): Call<EventResponse>

    //  Get Event by ID
    @GET("events/{id}")
    fun getEvent(@Path("id") id: String): Call<DetailResponse>

    @GET("events/{name}")
    suspend fun getFavEventByName(@Path("name") name: String): DetailResponse
}