package com.codelabs.api_client.exception

import com.codelabs.api_client.exception.ApiException.DeserializationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ApiExceptionTest {
    @Test
    fun testNetworkException() {
        val errorMessage = "Network error"
        val exception: ApiException = ApiException.NetworkException(errorMessage)
        assertNotNull(exception)
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun testServerException() {
        val errorMessage = "Server error"
        val exception = ApiException.ServerException(errorMessage)
        assertNotNull(exception)
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun testAuthenticationException() {
        val errorMessage = "Auth failed"
        val exception: ApiException = ApiException.AuthenticationException(errorMessage)
        assertNotNull(exception)
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun testDeserializationException() {
        val errorMessage = "Deserialization error"
        val exception: ApiException = DeserializationException(errorMessage)
        assertNotNull(exception)
        assertEquals(errorMessage, exception.message)
    }
}