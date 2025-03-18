package com.codelabs.api_client.client

import com.codelabs.api_client.model.ApiCharacter
import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("public/characters")
    suspend fun getCharactersResult(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ApiResult<ApiCharacter>>

    @GET("public/comics")
    suspend fun getComicsResult(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ApiResult<ApiComic>>
}