package com.example.mockrepoapp.domain

import com.example.mockrepoapp.model.MemesResponse
import com.example.mockrepoapp.network.APIService
import com.example.mockrepoapp.network.ApiCaller
import com.example.mockrepoapp.network.MockApiCaller
import com.example.mockrepoapp.network.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitHelperImpl @Inject constructor(
    private val apiService: APIService,
    private val mockApiCaller: MockApiCaller,
    private val apiCaller: ApiCaller
) : RetrofitHelper {
    override suspend fun getMockAPI(path: String): Flow<ResponseState<MemesResponse>>  =  mockApiCaller.mockApi<MemesResponse>(path)


    override suspend fun getMemes(
        isMockEnabled: Boolean,
        jsonPath: String
    ): Flow<ResponseState<MemesResponse>> = apiCaller.safeAPICall(isMockEnabled, jsonPath) {
        apiService.getMemes()
    }
}