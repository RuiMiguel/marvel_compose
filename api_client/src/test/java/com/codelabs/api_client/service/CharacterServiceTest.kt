package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.model.ApiCharacter
import com.codelabs.api_client.model.ApiData
import com.codelabs.api_client.model.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class CharacterServiceTest {
    private lateinit var apiClient: ApiClient
    private lateinit var characterService: CharacterService

    @BeforeEach
    fun setUp() {
        apiClient = mockk(relaxed = true)
        characterService = CharacterService(apiClient)
    }

    @Test
    fun `getCharactersResult should return successful response`() = runBlocking {
        val apiCharacters = listOf(
            ApiCharacter(
                id = 1,
                name = "Character 1",
                description = "Description 1",
                modified = "Modified 1",
                thumbnail = null,
                resourceURI = null,
                urls = null,
            ),
        )
        val apiResult = ApiResult(
            code = 200,
            status = "OK",
            copyright = "Copyright",
            attributionText = "Attribution Text",
            attributionHTML = "Attribution HTML",
            data = ApiData(
                offset = 0,
                limit = 10,
                total = 10,
                count = 10,
                results = apiCharacters,
            ),
            results = "results"
        )
        val response: Response<ApiResult<ApiCharacter>> = Response.success(apiResult)

        coEvery { apiClient.getCharactersResult(any(), any()) } returns response

        val result = characterService.getCharactersResult(10, 0)

        assertNotNull(result)
        assertEquals(200, result.code())
        assertEquals(apiCharacters, result.body()?.results)
    }

    @Test
    fun `getCharactersResult should return error response`() = runBlocking {
        val response: Response<ApiResult<ApiCharacter>> =
            Response.error(404, "Not Found".toResponseBody(null))

        coEvery { apiClient.getCharactersResult(any(), any()) } returns response

        val result = characterService.getCharactersResult(10, 0)

        assertNotNull(result)
        assertEquals(404, result.code())
    }
}