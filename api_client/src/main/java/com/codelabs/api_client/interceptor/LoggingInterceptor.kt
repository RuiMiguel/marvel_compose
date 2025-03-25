package com.codelabs.api_client.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

@Suppress("unused")
class LoggingInterceptor(private var logEnabled: Boolean, private val logger: Logger) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (logEnabled) {
            logger.info("HTTP Request: ${request.method} ${request.url}")
            logger.info("headers: ${request.headers}")
        }

        return try {
            val response = chain.proceed(request)

            if (logEnabled) {
                logger.info("HTTP Response: ${response.code} ${response.body}")
            }
            response
        } catch (exception: IOException) {
            if (logEnabled) {
                logger.log(Level.SEVERE, "HTTP Error: ${exception.message}")
            }
            throw exception
        }
    }
}