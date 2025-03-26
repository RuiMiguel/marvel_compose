package com.codelabs.comic_repository.mapper

import com.codelabs.api_client.model.ApiComic
import com.codelabs.api_client.model.ApiComicImage
import com.codelabs.api_client.model.ApiPrice
import com.codelabs.api_client.model.ApiResult
import com.codelabs.api_client.model.ApiTextObject
import com.codelabs.api_client.model.ApiThumbnail
import com.codelabs.api_client.model.ApiUrl
import com.codelabs.domain.model.DomainComic
import com.codelabs.domain.model.DomainComicImage
import com.codelabs.domain.model.DomainData
import com.codelabs.domain.model.DomainPrice
import com.codelabs.domain.model.DomainResult
import com.codelabs.domain.model.DomainTextObject
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl

fun ApiResult<ApiComic>.toDomainResult(): DomainResult<DomainComic> {
    return DomainResult(
        code = code ?: 0,
        status = status ?: "",
        copyright = copyright ?: "",
        data = DomainData(
            offset = data?.offset ?: 0,
            limit = data?.limit ?: 0,
            total = data?.total ?: 0,
            count = data?.count ?: 0,
            results = data?.results?.map { it.toDomainComic() } ?: emptyList()
        ),
        attributionText = attributionText ?: "",
        attributionHTML = attributionHTML ?: "",
        results = results ?: "",
    )
}

fun ApiComic.toDomainComic(): DomainComic {
    return DomainComic(
        id = id ?: 0,
        digitalId = digitalId ?: 0,
        title = title ?: "",
        issueNumber = issueNumber ?: 0.0,
        variantDescription = variantDescription ?: "",
        description = description ?: "",
        modified = modified ?: "",
        isbn = isbn ?: "",
        upc = upc ?: "",
        diamondCode = diamondCode ?: "",
        ean = ean ?: "",
        issn = issn ?: "",
        format = format ?: "",
        pageCount = pageCount ?: 0,
        textObjects = textObjects?.map { it.toDomainTextObject() } ?: emptyList(),
        resourceURI = resourceURI ?: "",
        urls = urls?.map { it.toDomainUrl() } ?: emptyList(),
        prices = prices?.map { it.toDomainPrice() } ?: emptyList(),
        thumbnail = thumbnail?.toDomainThumbnail() ?: emptyDomainThumbnail(),
        images = images?.map { it.toDomainComicImage() } ?: emptyList()
    )
}

fun ApiTextObject.toDomainTextObject(): DomainTextObject {
    return DomainTextObject(
        type = type ?: "",
        language = language ?: "",
        text = text ?: ""
    )
}

fun ApiUrl.toDomainUrl(): DomainUrl {
    return DomainUrl(
        type = type ?: "",
        url = url ?: ""
    )
}

fun ApiPrice.toDomainPrice(): DomainPrice {
    return DomainPrice(
        type = type ?: "",
        price = price ?: ""
    )
}

fun ApiThumbnail.toDomainThumbnail(): DomainThumbnail {
    return DomainThumbnail(
        path = path ?: "",
        extension = extension ?: ""
    )
}

private fun emptyDomainThumbnail() = DomainThumbnail("", "")

fun ApiComicImage.toDomainComicImage(): DomainComicImage {
    return DomainComicImage(
        path = path ?: "",
        extension = extension ?: ""
    )
}