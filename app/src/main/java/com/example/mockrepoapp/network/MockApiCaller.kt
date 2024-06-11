package com.example.mockrepoapp.network

import com.example.mockrepoapp.utils.Constant.appContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer
import java.net.SocketException
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class MockApiCaller @Inject constructor() {

    suspend inline fun <reified T> mockApi(jsonFilePath: String?): Flow<ResponseState<T>> = flow {
        try {
            emit(ResponseState.Loading(true))
            if (jsonFilePath.isNullOrEmpty()){
                emit(ResponseState.Error(DataError.Network.NOT_FOUND))
            }else{
                val jsonString: String = returnJsonResponseAsString(jsonFilePath)
                val jsonObject = Gson().fromJson<T>(jsonString, object : TypeToken<T>() {}.type)
                val res: Response<T> = Response.success(jsonObject)
                emit(ResponseState.Success<T>(res.body()!!))
            }
            emit(ResponseState.Loading(false))
        }catch (e:Exception){
            emit(ResponseState.Loading(false))
            when (e) {
                is HttpException -> emit(ResponseState.Error(DataError.Network.HTTP_EXCEPTION))
                is SocketException -> emit(ResponseState.Error(DataError.Network.SOCKET_EXCEPTION))
                is IOException -> emit(ResponseState.Error(DataError.Network.IO_EXCEPTION))
                else -> emit(ResponseState.Error(DataError.Network.UNKNOWN))
            }
        }
    }.flowOn(Dispatchers.IO)
    fun returnJsonResponseAsString(jsonFilePath: String): String {
        val jsonFile = appContext.resources.assets.open(jsonFilePath)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        return try {
            val reader: Reader = BufferedReader(InputStreamReader(jsonFile, StandardCharsets.UTF_8))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
            jsonFile.close()
            writer.toString()
        } catch (e: java.lang.Exception) {
            "{\"message\": \"${e.message}\"}"
        }
    }
}
