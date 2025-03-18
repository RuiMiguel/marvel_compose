package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient

class CharacterService(private val apiClient: ApiClient) {
    suspend fun getCharactersResult(limit: Int, offset: Int) {
        apiClient.getCharactersResult(limit = limit, offset = offset)
    }
}