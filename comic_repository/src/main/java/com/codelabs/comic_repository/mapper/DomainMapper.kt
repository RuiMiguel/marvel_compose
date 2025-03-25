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
        code = code,
        status = status,
        copyright = copyright,
        data = DomainData(
            offset = data?.offset,
            limit = data?.limit,
            total = data?.total,
            count = data?.count,
            results = data?.results?.map { it.toDomainComic() }
        ),
        attributionText = attributionText,
        attributionHTML = attributionHTML,
        results = results,
    )
}

fun ApiComic.toDomainComic(): DomainComic {
    return DomainComic(
        id = id,
        digitalId = digitalId,
        title = title,
        issueNumber = issueNumber,
        variantDescription = variantDescription,
        description = description,
        modified = modified,
        isbn = isbn,
        upc = upc,
        diamondCode = diamondCode,
        ean = ean,
        issn = issn,
        format = format,
        pageCount = pageCount,
        textObjects = textObjects?.map { it.toDomainTextObject() },
        resourceURI = resourceURI,
        urls = urls?.map { it.toDomainUrl() },
        prices = prices?.map { it.toDomainPrice() },
        thumbnail = thumbnail?.toDomainThumbnail(),
        images = images?.map { it.toDomainComicImage() }
    )
}

fun ApiTextObject.toDomainTextObject(): DomainTextObject {
    return DomainTextObject(
        type = type,
        language = language,
        text = text
    )
}

fun ApiUrl.toDomainUrl(): DomainUrl {
    return DomainUrl(
        type = type,
        url = url
    )
}

fun ApiPrice.toDomainPrice(): DomainPrice {
    return DomainPrice(
        type = type,
        price = price
    )
}

fun ApiThumbnail.toDomainThumbnail(): DomainThumbnail {
    return DomainThumbnail(
        path = path,
        extension = extension
    )
}

fun ApiComicImage.toDomainComicImage(): DomainComicImage {
    return DomainComicImage(
        path = path,
        extension = extension
    )
}