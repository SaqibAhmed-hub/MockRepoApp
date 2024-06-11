package com.example.mockrepoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mockrepoapp.domain.RetrofitHelper
import com.example.mockrepoapp.model.MemesResponse
import com.example.mockrepoapp.network.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiHelper: RetrofitHelper
) : ViewModel() {

    private val _getMemesLiveData: MutableLiveData<ResponseState<MemesResponse>> = MutableLiveData()
    val getMemesLiveData: LiveData<ResponseState<MemesResponse>> = _getMemesLiveData


    fun getMockAPI() {
        viewModelScope.launch {
            apiHelper.getMockAPI("json/memesResponse.json").collect {
                _getMemesLiveData.value = it
            }
        }
    }

    fun getMemesAPI() {
        viewModelScope.launch {
            apiHelper.getMemes(true,"json/memesResponse.json").collect { state ->
                _getMemesLiveData.value = state
            }
        }
    }


}