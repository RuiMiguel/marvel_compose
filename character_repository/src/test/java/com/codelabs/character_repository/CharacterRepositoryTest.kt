package com.codelabs.character_repository

import com.codelabs.api_client.model.ApiCharacter
import com.codelabs.api_client.model.ApiData
import com.codelabs.api_client.model.ApiResult
import com.codelabs.api_client.service.CharacterService
import com.codelabs.character_repository.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class CharacterRepositoryTest {
    private lateinit var characterRepository: CharacterRepository
    private lateinit var characterService: CharacterService

    @BeforeEach
    fun setUp() {
        characterService = mockk(relaxed = true)
        characterRepository = CharacterRepository(characterService)
    }

    @Test
    fun `getCharactersResult should return mapped DomainResult when API call is successful`() =
        runBlocking {
            val fakeApiResult = ApiResult<ApiCharacter>(
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

            coEvery { characterService.getCharactersResult(10, 0) } returns fakeApiResult

            val result = characterRepository.getCharactersResult(10, 0)

            assertNotNull(result)
            assertEquals(200, result.code)
            assertEquals("Success", result.status)
        }

    @Test
    fun `getCharactersResult should throw exception when API call fails`() = runBlocking {
        coEvery {
            characterService.getCharactersResult(any(), any())
        } throws Exception("Network error")

        val exception = assertFailsWith<Exception> {
            characterRepository.getCharactersResult(10, 0)
        }
        assertEquals("Network error", exception.message)
    }
}