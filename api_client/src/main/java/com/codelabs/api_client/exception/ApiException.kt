package com.codelabs.api_client.exception

import java.io.IOException

sealed class ApiException(val msg: String?): IOException() {
    class NetworkException(message: String?): ApiException(message)
    class ServerException(message: String?): ApiException(message)
    class AuthenticationException(message: String?): ApiException(message)
    class DeserializationException(message: String?): ApiException(message)
}