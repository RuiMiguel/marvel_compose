package com.codelabs.api_client.interceptor

import com.codelabs.api_client.exception.ApiException.AuthenticationException
import com.codelabs.api_client.security.Security
import com.codelabs.secure_storage.SecureStorage
import io.mockk.coEvery
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

internal class AuthInterceptorTest {
    private lateinit var security: Security
    private lateinit var secureStorage: SecureStorage
    private lateinit var chain: Interceptor.Chain
    private lateinit var authInterceptor: AuthInterceptor

    private val testHash = "testHash"
    private val testTimestamp = "1234567890"
    private val testPublicKey = "testPublicKey"
    private val testPrivateKey = "testPrivateKey"

    @BeforeEach
    fun setUp() {
        security = mockk()
        secureStorage = mockk()
        chain = mockk()

        authInterceptor =
            AuthInterceptor(security = security, secureStorage = secureStorage)

        every { security.hashTimestamp(any(), any()) } returns Pair(testHash, testTimestamp)
        coEvery { secureStorage.publicKey() } returns testPublicKey
        coEvery { secureStorage.privateKey() } returns testPrivateKey
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
        every { security.hashTimestamp(any(), any()) } throws IOException("Test Exception")

        assertThrows<AuthenticationException> { authInterceptor.intercept(chain) }
    }
}