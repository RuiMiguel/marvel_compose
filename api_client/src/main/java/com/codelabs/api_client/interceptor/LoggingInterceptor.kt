package com.codelabs.api_client.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

@Suppress("unused")
class LoggingInterceptor(private var logEnabled: Boolean) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (logEnabled) {
            Log.d("HTTP Request", "${request.method} ${request.url}")
            Log.d("headers", "${request.headers}")
        }

        return try {
            val response = chain.proceed(request)

            if (logEnabled) {
                Log.d("HTTP Response","${response.code} ${response.body}")
            }
            response
        }
        catch (exception: IOException) {
            if (logEnabled) {
                Log.e("HTTP Error", "${exception.message}")
            }
            throw exception
        }
    }
}