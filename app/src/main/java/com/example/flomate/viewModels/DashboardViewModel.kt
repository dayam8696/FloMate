package com.example.flomate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flomate.controller.ApiResult
import com.example.flomate.dashboard_repositories.DashboardRepository
import com.example.flomate.model.addData.AddDataRequest
import com.example.flomate.model.data_list.ListDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DashboardViewModel @Inject constructor(private val repo: DashboardRepository) :
    ViewModel() {


    private val _getListTestResponse = MutableLiveData<ApiResult<ListDataResponse>>()
    val getListTestResponse: LiveData<ApiResult<ListDataResponse>>
        get() = _getListTestResponse

    fun getListTest(email: String) {
        viewModelScope.launch {
            _getListTestResponse.value = repo.getListTest(email)
        }
    }


    private val _setDatesResponse = MutableLiveData<ApiResult<Unit>>()
    val setDatesResponse: LiveData<ApiResult<Unit>>
        get() = _setDatesResponse

    fun setDates(addDataRequest: AddDataRequest) {
        viewModelScope.launch {
            _setDatesResponse.value = repo.setData(addDataRequest)
        }
    }


}