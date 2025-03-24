package com.codelabs.api_client.interceptor

import com.codelabs.api_client.exception.ApiException.AuthenticationException
import com.codelabs.api_client.security.Security
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class AuthInterceptorTest {

    private val security: Security = mockk()
    private val chain: Interceptor.Chain = mockk()
    private val authInterceptor = AuthInterceptor(security)

    private val testHash = "testHash"
    private val testTimestamp = "1234567890"
    private val testPublicKey = "testPublicKey"

    @BeforeEach
    fun setUp() {
        every { security.hashTimestamp() } returns Pair(testHash, testTimestamp)
        every { security.publicKey } returns testPublicKey
    }

    @Test
    fun `intercept should add authentication parameters and proceed`() {
        val originalRequest = Request.Builder().url("https://example.com").build()
        val modifiedRequestSlot = slot<Request>()

        every { chain.request() } returns originalRequest
        every { chain.proceed(capture(modifiedRequestSlot)) } returns Response.Builder()
            .request(originalRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("{}".toResponseBody("application/json".toMediaType()))
            .build()

        val response = authInterceptor.intercept(chain)

        assertNotNull(response)
        assertEquals(200, response.code)
        verify { chain.proceed(any()) }

        val modifiedRequest = modifiedRequestSlot.captured
        val url = modifiedRequest.url

        assertEquals(testTimestamp, url.queryParameter("ts"))
        assertEquals(testHash, url.queryParameter("hash"))
        assertEquals(testPublicKey, url.queryParameter("apikey"))
    }

    @Test
    fun `intercept should throw AuthenticationException on IOException`() {
        val originalRequest = Request.Builder().url("https://example.com").build()

        every { chain.request() } returns originalRequest
        every { security.hashTimestamp() } throws IOException("Test Exception")

        assertThrows<AuthenticationException> { authInterceptor.intercept(chain) }
    }
}