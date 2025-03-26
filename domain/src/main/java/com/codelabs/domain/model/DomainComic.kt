package com.codelabs.domain.model

class DomainComic(
    val id: Int,
    val digitalId: Int,
    val title: String,
    val issueNumber: Double,
    val variantDescription: String,
    val description: String,
    val modified: String,
    val isbn: String,
    val upc: String,
    val diamondCode: String,
    val ean: String,
    val issn: String,
    val format: String,
    val pageCount: Int,
    val textObjects: List<DomainTextObject>,
    val resourceURI: String,
    val urls: List<DomainUrl>,
    val prices: List<DomainPrice>,
    val thumbnail: DomainThumbnail,
    val images: List<DomainComicImage>
    )

class DomainTextObject(
    val type: String,
    val language: String,
    val text: String
)

class DomainPrice(
    val type: String,
    val price: String
)

class DomainComicImage(
    val path: String,
    val extension: String
)