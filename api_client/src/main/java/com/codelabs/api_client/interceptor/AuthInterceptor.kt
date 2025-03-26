package com.codelabs.api_client.interceptor

import com.codelabs.api_client.exception.ApiException.AuthenticationException
import com.codelabs.api_client.security.Security
import com.codelabs.secure_storage.SecureStorage
import kotlinx.coroutines.async
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlinx.coroutines.runBlocking

class AuthInterceptor(private val security: Security, private val secureStorage: SecureStorage) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        return try {
            val (privateKey, publicKey) = runBlocking {
                val privateKeyDeferred = async { secureStorage.privateKey() }
                val publicKeyDeferred = async { secureStorage.publicKey() }
                privateKeyDeferred.await() to publicKeyDeferred.await()
            }

            val (hash, timestamp) = security.hashTimestamp(
                privateKey = privateKey,
                publicKey = publicKey,
            )

            val queryParameters = mutableMapOf(
                "ts" to timestamp,
                "hash" to hash,
                "apikey" to publicKey
            ).toMap()

            val url = originalRequest.url.newBuilder().apply {
                queryParameters.forEach {
                    val (key, value) = it
                    addQueryParameter(key, value)
                }
            }.build()

            val builder = originalRequest.newBuilder().url(url)

            val request = builder.method(originalRequest.method, originalRequest.body).build()
            chain.proceed(request)
        } catch (exception: IOException) {
            throw AuthenticationException(exception.message)
        }
    }
}