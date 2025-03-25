package com.codelabs.api_client.interceptor

import com.sun.org.slf4j.internal.Logger
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

@Suppress("unused")
class LoggingInterceptor(private var logEnabled: Boolean, private val logger: Logger) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (logEnabled) {
            logger.debug("HTTP Request: ${request.method} ${request.url}")
            logger.debug("headers: ${request.headers}")
        }

        return try {
            val response = chain.proceed(request)

            if (logEnabled) {
                logger.debug("HTTP Response: ${response.code} ${response.body}")
            }
            response
        } catch (exception: IOException) {
            if (logEnabled) {
                logger.debug("HTTP Error: ${exception.message}")
            }
            throw exception
        }
    }
}