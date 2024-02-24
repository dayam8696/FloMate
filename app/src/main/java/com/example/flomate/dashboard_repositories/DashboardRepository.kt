package com.example.flomate.dashboard_repositories

import com.example.flomate.controller.ApiResult
import com.example.flomate.controller.CommonApiService
import com.example.flomate.controller.networkCall
import com.example.flomate.model.data_list.ListDataResponse
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val api: CommonApiService) {

    suspend fun getListTest(email: String): ApiResult<ListDataResponse> {
        return networkCall(api.getListTest(email))
    }

}