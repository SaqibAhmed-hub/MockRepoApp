package com.example.mockrepoapp.domain

import com.example.mockrepoapp.model.MemesResponse
import com.example.mockrepoapp.network.ResponseState
import kotlinx.coroutines.flow.Flow

interface RetrofitHelper {

    suspend fun getMockAPI(path: String): Flow<ResponseState<MemesResponse>>

    suspend fun getMemes(isMockEnabled:Boolean, jsonPath : String): Flow<ResponseState<MemesResponse>>

}