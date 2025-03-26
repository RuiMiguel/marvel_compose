package com.codelabs.comic_repository.mapper

import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiComicImage
import com.codelabs.api_client.model.ApiPrice
import com.codelabs.api_client.model.ApiTextObject
import com.codelabs.api_client.model.ApiThumbnail
import com.codelabs.api_client.model.ApiUrl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DomainMapperTests {
    @Test
    fun `test ApiComic to DomainComic mapping`() {
        val apiComic = ApiComic(
            id = 1,
            digitalId = 1234,
            title = "Avengers #1",
            issueNumber = 1.0,
            variantDescription = "First Issue",
            description = "An epic beginning",
            modified = "2024-01-01T00:00:00Z",
            isbn = "123456789",
            upc = "789456123",
            diamondCode = "DIAMOND123",
            ean = "EAN12345",
            issn = "ISSN6789",
            format = "Comic",
            pageCount = 32,
            textObjects = listOf(ApiTextObject("summary", "en", "An epic tale.")),
            resourceURI = "http://example.com/comic",
            urls = listOf(ApiUrl("detail", "http://example.com/comic/detail")),
            prices = listOf(ApiPrice("printPrice", "3.99")),
            thumbnail = ApiThumbnail("http://example.com/comic/image", "jpg"),
            images = listOf(ApiComicImage("http://example.com/comic/extra", "jpg"))
        )

        val domainComic = apiComic.toDomainComic()

        assertEquals(1, domainComic.id)
        assertEquals("Avengers #1", domainComic.title)
        assertEquals(1.0, domainComic.issueNumber)
        assertEquals("First Issue", domainComic.variantDescription)
        assertEquals("An epic beginning", domainComic.description)
        assertEquals("Comic", domainComic.format)
        assertEquals(32, domainComic.pageCount)
        assertEquals("summary", domainComic.textObjects.first().type)
        assertEquals("printPrice", domainComic.prices.first().type)
        assertEquals("http://example.com/comic/image", domainComic.thumbnail.path)
        assertEquals("http://example.com/comic/extra", domainComic.images.first().path)
    }

    @Test
    fun `test ApiTextObject to DomainTextObject mapping`() {
        val apiTextObject = ApiTextObject(type = "wiki", language = "en", text = "Some text")
        val domainTextObject = apiTextObject.toDomainTextObject()

        assertEquals("wiki", domainTextObject.type)
        assertEquals("en", domainTextObject.language)
        assertEquals("Some text", domainTextObject.text)
    }

    @Test
    fun `test ApiUrl to DomainUrl mapping`() {
        val apiUrl = ApiUrl(type = "wiki", url = "http://example.com/wiki")
        val domainUrl = apiUrl.toDomainUrl()

        assertEquals("wiki", domainUrl.type)
        assertEquals("http://example.com/wiki", domainUrl.url)
    }

    @Test
    fun `test ApiPrice to DomainPrice mapping`() {
        val apiPrice = ApiPrice(type = "euros", price = "3.99")
        val domainPrice = apiPrice.toDomainPrice()

        assertEquals("euros", domainPrice.type)
        assertEquals("3.99", domainPrice.price)
    }

    @Test
    fun `test ApiThumbnail to DomainThumbnail mapping`() {
        val apiThumbnail = ApiThumbnail(path = "http://example.com/image", extension = "jpg")
        val domainThumbnail = apiThumbnail.toDomainThumbnail()

        assertEquals("http://example.com/image", domainThumbnail.path)
        assertEquals("jpg", domainThumbnail.extension)
    }

    @Test
    fun `test ApiComicImage to DomainComicImage mapping`() {
        val apiComicImage = ApiComicImage(path = "http://example.com/image", extension = "jpg")
        val domainComicImage = apiComicImage.toDomainComicImage()

        assertEquals("http://example.com/image", domainComicImage.path)
        assertEquals("jpg", domainComicImage.extension)
    }
}