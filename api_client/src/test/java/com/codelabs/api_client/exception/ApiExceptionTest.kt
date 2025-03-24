package com.codelabs.api_client.exception

import com.codelabs.api_client.exception.ApiException.DeserializationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ApiExceptionTest {
    @Test
    fun testNetworkException() {
        val exception: ApiException = ApiException.NetworkException("Network error")
        assertNotNull(exception)
        assertEquals("Network error", exception.msg)
    }

    @Test
    fun testServerException() {
        val exception = ApiException.ServerException("Server error")
        assertNotNull(exception)
        assertEquals("Server error", exception.msg)
    }

    @Test
    fun testAuthenticationException() {
        val exception: ApiException = ApiException.AuthenticationException("Auth failed")
        assertNotNull(exception)
        assertEquals("Auth failed", exception.msg)
    }

    @Test
    fun testDeserializationException() {
        val exception: ApiException = DeserializationException("Deserialization error")
        assertNotNull(exception)
        assertEquals("Deserialization error", exception.msg)
    }
}