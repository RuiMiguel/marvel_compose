package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.exception.ApiException
import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiResult
import com.squareup.moshi.JsonDataException
import java.io.IOException

class ComicService(private val apiClient: ApiClient) {
    suspend fun getComicsResult(limit: Int, offset: Int): ApiResult<ApiComic> {
        try {
            val response = apiClient.getComicsResult(limit = limit, offset = offset)
            if (response.isSuccessful) {
                val data = response.body() ?: throw ApiException.ServerException("Empty data")
                return data
            } else {
                throw ApiException.ServerException(response.errorBody()?.string())
            }
        } catch (error: IOException) {
            throw ApiException.NetworkException(error.message)
        } catch (error: JsonDataException) {
            throw ApiException.DeserializationException(error.message)
        }
    }
}