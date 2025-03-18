package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient

class ComicService(private val apiClient: ApiClient) {
    suspend fun getComicsResult(limit: Int, offset: Int) {
        apiClient.getComicsResult(limit = limit, offset = offset)
    }
}