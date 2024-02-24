package com.example.flomate.controller

import com.example.flomate.model.addData.AddDataRequest
import com.example.flomate.model.data_list.ListDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommonApiService {

    @GET("getdate")
    suspend fun getListTest(@Query("email") email: String): Response<ListDataResponse>

    @POST("create-date")
    suspend fun setData(@Body addDataRequest: AddDataRequest): Response<Unit>

}