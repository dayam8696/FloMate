package com.example.flomate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flomate.controller.ApiResult
import com.example.flomate.dashboard_repositories.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DashboardViewModel @Inject constructor(private val repo: DashboardRepository) :
    ViewModel() {

//    private val _addVariantAttributeResponse =
//        MutableLiveData<ApiResult<AddAttributesTypeBulkResponse>>()
//    val addVariantAttributeResponse: LiveData<ApiResult<AddAttributesTypeBulkResponse>>
//        get() = _addVariantAttributeResponse
//
//    fun addVariantAttribute(productVariationModel: ProductVariationModel) {
//        checkRefreshToken {
//            viewModelScope.launch {
//                _addVariantAttributeResponse.value = ApiResult.Loading
//                _addVariantAttributeResponse.value =
//                    repo.addAttributeTypeBulk(productVariationModel)
//            }
//        }
//    }


    private val _getListTestResponse = MutableLiveData<ApiResult<Unit>>()
    val getListTestResponse: LiveData<ApiResult<Unit>>
        get() = _getListTestResponse

    fun getListTest() {
        viewModelScope.launch {
            _getListTestResponse.value = repo.getListTest()
        }
    }


}