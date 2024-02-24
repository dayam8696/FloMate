package com.example.flomate.controller

import retrofit2.Response
import retrofit2.http.GET

interface CommonApiService {

    @GET("getdate?email=husn1%40gmail.com")
    suspend fun getListTest(): Response<Unit>

}