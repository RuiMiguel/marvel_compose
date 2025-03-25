package com.codelabs.api_client.exception

sealed class ApiException(override val message: String?): Exception() {
    class NetworkException(message: String?): ApiException(message)
    class ServerException(message: String?): ApiException(message)
    class AuthenticationException(message: String?): ApiException(message)
    class DeserializationException(message: String?): ApiException(message)
}