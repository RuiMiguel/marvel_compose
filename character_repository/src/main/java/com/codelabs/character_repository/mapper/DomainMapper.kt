package com.codelabs.character_repository.mapper

import com.codelabs.api_client.model.ApiCharacter
import com.codelabs.api_client.model.ApiResult
import com.codelabs.api_client.model.ApiThumbnail
import com.codelabs.api_client.model.ApiUrl
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.domain.model.DomainData
import com.codelabs.domain.model.DomainResult
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl

fun ApiResult<ApiCharacter>.toDomainResult(): DomainResult<DomainCharacter> {
    return DomainResult(
        code = code ?: 0,
        status = status ?: "",
        copyright = copyright ?: "",
        data = DomainData(
            offset = data?.offset ?: 0,
            limit = data?.limit ?: 0,
            total = data?.total ?: 0,
            count = data?.count ?: 0,
            results = data?.results?.map { it.toDomainCharacter() } ?: emptyList(),
        ),
        attributionText = attributionText ?: "",
        attributionHTML = attributionHTML ?: "",
        results = results ?: "",
    )
}

fun ApiCharacter.toDomainCharacter(): DomainCharacter {
    return DomainCharacter(
        id = id ?: 0,
        name = name ?: "",
        description = description ?: "",
        modified = modified ?: "",
        resourceURI = resourceURI ?: "",
        urls = urls?.map { it.toDomainUrl() } ?: emptyList(),
        thumbnail = thumbnail?.toDomainThumbnail() ?: emptyDomainThumbnail()
    )
}

fun ApiUrl.toDomainUrl(): DomainUrl {
    return DomainUrl(
        type = type ?: "",
        url = url ?: ""
    )
}

fun ApiThumbnail.toDomainThumbnail(): DomainThumbnail {
    return DomainThumbnail(
        path = path ?: "",
        extension = extension ?: ""
    )
}

private fun emptyDomainThumbnail() = DomainThumbnail("", "")