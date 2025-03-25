package com.codelabs.api_client.service

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.exception.ApiException
import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiData
import com.codelabs.api_client.model.ApiResult
import com.squareup.moshi.JsonDataException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFailsWith

internal class ComicServiceTest {
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
                urls = null,
                title = "Title 1",
                issueNumber = 1.0,
                variantDescription = null,
                prices = null,
                digitalId = 1,
                issn = null,
                upc = null,
                diamondCode = null,
                ean = null,
                isbn = null,
                format = null,
                pageCount = 1,
                textObjects = null,
                images = null,
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
        assertEquals(apiComics, result.data?.results)
    }

    @Test
    fun `getComicsResult should throw ServerException when response is unsuccessful`() = runBlocking {
        val errorMessage = "Internal Server Error"
        val response: Response<ApiResult<ApiComic>> = Response.error(500, errorMessage.toResponseBody(null))

        coEvery { apiClient.getComicsResult(any(), any()) } returns response

        val exception = assertFailsWith<ApiException.ServerException> {
            comicService.getComicsResult(10, 0)
        }

        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getComicsResult should throw ServerException when data is empty`() = runBlocking {
        val errorMessage = "Empty data"
        val response: Response<ApiResult<ApiComic>> = Response.success(null)

        coEvery { apiClient.getComicsResult(any(), any()) } returns response

        val exception = assertFailsWith<ApiException.ServerException> {
            comicService.getComicsResult(10, 0)
        }

        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getComicsResult should throw NetworkException on IOException`() = runBlocking {
        val errorMessage = "Network error"

        coEvery { apiClient.getComicsResult(any(), any()) } throws IOException(errorMessage)

        val exception = assertFailsWith<ApiException.NetworkException> {
            comicService.getComicsResult(10, 0)
        }

        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getComicsResult should throw DeserializationException on JsonDataException`() = runBlocking {
        val errorMessage = "JSON parsing error"

        coEvery { apiClient.getComicsResult(any(), any()) } throws JsonDataException(errorMessage)

        val exception = assertFailsWith<ApiException.DeserializationException> {
            comicService.getComicsResult(10, 0)
        }

        assertEquals(errorMessage, exception.message)
    }
}