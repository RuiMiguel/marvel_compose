package com.codelabs.api_client.interceptor

import io.mockk.every
import io.mockk.mockk
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
import java.util.logging.Level
import java.util.logging.Logger

class LoggingInterceptorTest {

    private val testRequest = Request.Builder().url("https://example.com").build()
    private lateinit var chain: Interceptor.Chain
    private lateinit var logger: Logger

    private lateinit var loggingInterceptor: LoggingInterceptor

    @BeforeEach
    fun setUp() {
        chain = mockk()
        logger = mockk(relaxed = true)

        every { logger.info(any<String>()) } returns Unit
        every { logger.log(any(), any<String>()) } returns Unit
    }

    @Test
    fun `intercept should log request and response when logging is enabled`() {
        loggingInterceptor = LoggingInterceptor(true, logger)

        val testResponse = Response.Builder()
            .request(testRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Response Body".toResponseBody("application/json".toMediaType()))
            .build()

        every { chain.request() } returns testRequest
        every { chain.proceed(testRequest) } returns testResponse

        val response = loggingInterceptor.intercept(chain)

        assertNotNull(response)
        assertEquals(200, response.code)

        verify { logger.info("HTTP Request: GET https://example.com") }
        verify { logger.info("headers: ${testRequest.headers}") }
        verify { logger.info("HTTP Response: 200 ResponseBody") }
    }

    @Test
    fun `intercept should not log when logging is disabled`() {
        loggingInterceptor = LoggingInterceptor(false, logger)

        val testResponse = Response.Builder()
            .request(testRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("Response Body".toResponseBody("application/json".toMediaType()))
            .build()

        every { chain.request() } returns testRequest
        every { chain.proceed(testRequest) } returns testResponse

        val response = loggingInterceptor.intercept(chain)

        assertNotNull(response)
        assertEquals(200, response.code)

        verify(exactly = 0) { logger.info(any<String>()) }
    }

    @Test
    fun `intercept should log errors and throw IOException when request fails`() {
        loggingInterceptor = LoggingInterceptor(true, logger)

        every { chain.request() } returns testRequest
        every { chain.proceed(testRequest) } throws IOException("Network error")

        val exception = assertThrows<IOException> { loggingInterceptor.intercept(chain) }

        assertEquals("Network error", exception.message)
        verify { logger.log(Level.SEVERE, "HTTP Error: Network error") }
    }
}