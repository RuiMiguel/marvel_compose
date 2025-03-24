package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.model.ApiCharacter
import com.codelabs.api_client.model.ApiResult
import retrofit2.Response

class CharacterService(private val apiClient: ApiClient) {
    suspend fun getCharactersResult(limit: Int, offset: Int): Response<ApiResult<ApiCharacter>> {
        val response = apiClient.getCharactersResult(limit = limit, offset = offset)
        return response
    }
}