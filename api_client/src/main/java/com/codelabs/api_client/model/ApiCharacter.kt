package com.codelabs.api_client.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiCharacter(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "modified") val modified: String?,
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "urls") val urls: List<ApiUrl>?,
    @Json(name = "thumbnail") val thumbnail: ApiThumbnail?
)