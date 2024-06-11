package com.example.mockrepoapp.network

sealed class ResponseState<out R> {
    data class Success<out T>(val data:T) : ResponseState<T>()
    data class Error<out E>(val msg: DataError.Network) : ResponseState<E>()
    data class Loading<out B>(val boolean: Boolean): ResponseState<B>()
}



sealed interface DataError {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        BAD_REQUEST,
        UN_AUTHORIZED,
        NOT_FOUND,
        CONFLICTED,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        UNKNOWN,
        HTTP_EXCEPTION,
        SOCKET_EXCEPTION,
        IO_EXCEPTION,
        EMPTY_RESPONSE
    }
}