package com.codelabs.character_repository.mapper

import com.codelabs.api_client.model.ApiCharacter
import com.codelabs.api_client.model.ApiData
import com.codelabs.api_client.model.ApiResult
import com.codelabs.api_client.model.ApiThumbnail
import com.codelabs.api_client.model.ApiUrl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DomainMapperTests {

    @Test
    fun `test ApiResult to DomainResult mapping`() {
        val apiCharacter = ApiCharacter(
            id = 1,
            name = "Spider-Man",
            description = "A superhero",
            modified = "2024-01-01T00:00:00Z",
            resourceURI = "http://example.com",
            urls = listOf(ApiUrl("detail", "http://example.com/detail")),
            thumbnail = ApiThumbnail("http://example.com/image", "jpg")
        )

        val apiResult = ApiResult(
            code = 200,
            status = "OK",
            copyright = "Marvel",
            data = ApiData(
                offset = 0,
                limit = 20,
                total = 100,
                count = 1,
                results = listOf(apiCharacter)
            ),
            attributionText = "Attribution",
            attributionHTML = "<p>Attribution</p>",
            results = "Some results"
        )

        val domainResult = apiResult.toDomainResult()

        assertEquals(200, domainResult.code)
        assertEquals("OK", domainResult.status)
        assertEquals("Marvel", domainResult.copyright)
        assertEquals(0, domainResult.data.offset)
        assertEquals(100, domainResult.data.total)
        assertEquals(1, domainResult.data.results.size)
        assertEquals("Spider-Man", domainResult.data.results[0].name)
    }

    @Test
    fun `test ApiCharacter to DomainCharacter mapping`() {
        val apiCharacter = ApiCharacter(
            id = 1,
            name = "Iron Man",
            description = "A billionaire superhero",
            modified = "2024-01-01T00:00:00Z",
            resourceURI = "http://example.com",
            urls = listOf(ApiUrl("wiki", "http://example.com/wiki")),
            thumbnail = ApiThumbnail("http://example.com/image", "jpg")
        )

        val domainCharacter = apiCharacter.toDomainCharacter()

        assertEquals(1, domainCharacter.id)
        assertEquals("Iron Man", domainCharacter.name)
        assertEquals("A billionaire superhero", domainCharacter.description)
        assertEquals("http://example.com", domainCharacter.resourceURI)
        assertEquals("wiki", domainCharacter.urls?.first()?.type)
        assertEquals("http://example.com/image", domainCharacter.thumbnail?.path)
    }

    @Test
    fun `test ApiUrl to DomainUrl mapping`() {
        val apiUrl = ApiUrl(type = "wiki", url = "http://example.com/wiki")
        val domainUrl = apiUrl.toDomainUrl()

        assertEquals("wiki", domainUrl.type)
        assertEquals("http://example.com/wiki", domainUrl.url)
    }

    @Test
    fun `test ApiThumbnail to DomainThumbnail mapping`() {
        val apiThumbnail = ApiThumbnail(path = "http://example.com/image", extension = "jpg")
        val domainThumbnail = apiThumbnail.toDomainThumbnail()

        assertEquals("http://example.com/image", domainThumbnail.path)
        assertEquals("jpg", domainThumbnail.extension)
    }
}
