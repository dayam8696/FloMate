package com.example.flomate.dashboard_repositories

import com.example.flomate.controller.ApiResult
import com.example.flomate.controller.CommonApiService
import com.example.flomate.controller.networkCall
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val api: CommonApiService) {

    suspend fun getListTest(): ApiResult<Unit> {
        return networkCall(api.getListTest())
    }

}