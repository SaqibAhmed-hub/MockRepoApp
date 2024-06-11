package com.example.mockrepoapp.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

class ApiCaller @Inject constructor() {
    suspend inline fun <reified T> safeAPICall(
        crossinline call: suspend () -> Response<T>
    )
    : Flow<ResponseState<T>> =
        flow {
            try {
                //TODO : Need to check the Internet Connection and handle the Error
                emit(ResponseState.Loading(true))
                val response: Response<T> = call.invoke()
                if (response.isSuccessful) {
                    //Range 200 - 300
                    if (response.code() == 200 && response.body() != null) {
                        emit(ResponseState.Success(response.body()!!))
                    }else{
                        emit(ResponseState.Error(DataError.Network.EMPTY_RESPONSE))
                    }
                    emit(ResponseState.Loading(false))
                } else {
                    emit(ResponseState.Loading(false))
                    emit(ResponseState.Error(handleNetworkError(response.code())))
                }
            } catch (e: Exception) {
                emit(ResponseState.Loading(false))
                when (e) {
                    is HttpException -> emit(ResponseState.Error(DataError.Network.HTTP_EXCEPTION))
                    is SocketException -> emit(ResponseState.Error(DataError.Network.SOCKET_EXCEPTION))
                    is IOException -> emit(ResponseState.Error(DataError.Network.IO_EXCEPTION))
                    else -> emit(ResponseState.Error(DataError.Network.UNKNOWN))
                }
            }
        }.flowOn(Dispatchers.IO)

    fun handleNetworkError(code: Int): DataError.Network = when (code) {
        400 -> DataError.Network.BAD_REQUEST
        401 -> DataError.Network.UN_AUTHORIZED
        404 -> DataError.Network.NOT_FOUND
        408 -> DataError.Network.REQUEST_TIMEOUT
        409 -> DataError.Network.CONFLICTED
        413 -> DataError.Network.PAYLOAD_TOO_LARGE
        500, 502, 505 -> DataError.Network.SERVER_ERROR
        else -> DataError.Network.UNKNOWN
    }

}