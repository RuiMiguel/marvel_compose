package com.codelabs.domain.model


class DomainResult<T>(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: DomainData<T>,
    val results: String,
)

class DomainData<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>,
)

class DomainUrl(
    val type: String,
    val url: String
)

class DomainThumbnail(
    val path: String,
    val extension: String
)

fun DomainThumbnail.getCharacterHomePreview() = "$path/standard_xlarge.$extension"
fun DomainThumbnail.getHomePreviewUrl() = "$path/portrait_xlarge.$extension"
