package com.codelabs.comic_repository.repository

import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiData
import com.codelabs.api_client.model.ApiResult
import com.codelabs.api_client.service.ComicService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class ComicRepositoryTest {
    private lateinit var comicRepository: ComicRepository
    private lateinit var comicService: ComicService

    @BeforeEach
    fun setUp() {
        comicService = mockk(relaxed = true)
        comicRepository = ComicRepository(comicService)
    }

    @Test
    fun `getComicsResult should return mapped DomainResult when API call is successful`() =
        runBlocking {
            val fakeApiResult = ApiResult<ApiComic>(
                code = 200,
                status = "Success",
                copyright = "Test",
                attributionText = "Test Attribution",
                attributionHTML = "<p>Test Attribution</p>",
                data = ApiData(
                    offset = 0,
                    limit = 10,
                    total = 100,
                    count = 10,
                    results = listOf()
                ),
                results = "Success"
            )

            coEvery { comicService.getComicsResult(10, 0) } returns fakeApiResult

            val result = comicRepository.getComicsResult(10, 0)

            assertNotNull(result)
            assertEquals(200, result.code)
            assertEquals("Success", result.status)
        }

    @Test
    fun `getComicsResult should throw exception when API call fails`() = runBlocking {
        coEvery { comicService.getComicsResult(any(), any()) } throws Exception("Network error")

        val exception = assertFailsWith<Exception> {
            comicRepository.getComicsResult(10, 0)
        }
        assertEquals("Network error", exception.message)
    }
}