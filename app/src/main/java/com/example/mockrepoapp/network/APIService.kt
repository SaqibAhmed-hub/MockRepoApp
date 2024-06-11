package com.example.mockrepoapp.network

import com.example.mockrepoapp.model.MemesResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("get_memes")
    suspend fun getMemes(): Response<MemesResponse>


}