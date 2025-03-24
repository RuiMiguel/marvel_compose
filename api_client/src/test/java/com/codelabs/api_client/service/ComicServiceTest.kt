package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.model.ApiComic
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

class ComicServiceTest {
    private lateinit var apiClient: ApiClient
    private lateinit var comicService: ComicService

    @BeforeEach
    fun setUp() {
        apiClient = mockk(relaxed = true)
        comicService = ComicService(apiClient)
    }

    @Test
    fun `getComicsResult should return successful response`() = runBlocking {
        val apiComics = listOf(
            ApiComic(
                id = 1,
                description = "Description 1",
                modified = "Modified 1",
                thumbnail = null,
                resourceURI = null,
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
                results = apiComics,
            ),
            results = "results"
        )
        val response: Response<ApiResult<ApiComic>> = Response.success(apiResult)

        coEvery { apiClient.getComicsResult(any(), any()) } returns response

        val result = comicService.getComicsResult(10, 0)

        assertNotNull(result)
        assertEquals(200, result.code())
        assertEquals(apiComics, result.body()?.results)
    }

    @Test
    fun `getComicsResult should return error response`() = runBlocking {
        val response: Response<ApiResult<ApiComic>> =
            Response.error(404, "Not Found".toResponseBody(null))

        coEvery { apiClient.getComicsResult(any(), any()) } returns response

        val result = comicService.getComicsResult(10, 0)

        assertNotNull(result)
        assertEquals(404, result.code())
    }
}