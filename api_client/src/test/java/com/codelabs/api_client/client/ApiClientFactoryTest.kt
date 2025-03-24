package com.codelabs.api_client.client

import com.codelabs.api_client.interceptor.AuthInterceptor
import com.codelabs.api_client.interceptor.LoggingInterceptor
import com.codelabs.api_client.security.Security
import com.squareup.moshi.Moshi
import io.mockk.mockk
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ApiClientFactoryTest {

 private val security: Security = mockk()
 private val baseUrl = "https://example.com"
 private val moshi: Moshi = Moshi.Builder().build()

 @Test
 fun `createAuthInterceptor should return an instance of AuthInterceptor`() {
  val authInterceptor = ApiClientFactory.createAuthInterceptor(security)
  assertNotNull(authInterceptor)
  assertEquals(AuthInterceptor::class, authInterceptor::class)
 }

 @Test
 fun `createLoggingInterceptor should return an instance of LoggingInterceptor`() {
  val loggingInterceptor = ApiClientFactory.createLoggingInterceptor(true)
  assertNotNull(loggingInterceptor)
  assertEquals(LoggingInterceptor::class, loggingInterceptor::class)
 }

 @Test
 fun `createOkHttpClient should return an instance of OkHttpClient`() {
  val authInterceptor = ApiClientFactory.createAuthInterceptor(security)
  val loggingInterceptor = ApiClientFactory.createLoggingInterceptor(true)

  val okHttpClient = ApiClientFactory.createOkHttpClient(authInterceptor, loggingInterceptor)

  assertNotNull(okHttpClient)
  assertEquals(OkHttpClient::class, okHttpClient::class)
 }

 @Test
 fun `createRetrofit should return an instance of Retrofit`() {
  val authInterceptor = ApiClientFactory.createAuthInterceptor(security)
  val loggingInterceptor = ApiClientFactory.createLoggingInterceptor(true)
  val okHttpClient = ApiClientFactory.createOkHttpClient(authInterceptor, loggingInterceptor)

  val retrofit = ApiClientFactory.createRetrofit(baseUrl, okHttpClient, moshi)

  assertNotNull(retrofit)
  assertEquals(Retrofit::class, retrofit::class)
 }
}
