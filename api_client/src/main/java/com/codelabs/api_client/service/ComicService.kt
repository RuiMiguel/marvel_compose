package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiResult
import retrofit2.Response

class ComicService(private val apiClient: ApiClient) {
    suspend fun getComicsResult(limit: Int, offset: Int): Response<ApiResult<ApiComic>> {
        val response = apiClient.getComicsResult(limit = limit, offset = offset)
        return response
    }
}