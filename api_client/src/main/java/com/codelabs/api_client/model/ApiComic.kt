package com.codelabs.api_client.model

import com.squareup.moshi.Json

class ApiComic(
    @Json(name = "id") val id: Int?,
    @Json(name = "digitalId") val digitalId: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "issueNumber") val issueNumber: Double?,
    @Json(name = "variantDescription") val variantDescription: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "modified") val modified: String?,
    @Json(name = "isbn") val isbn: String?,
    @Json(name = "upc") val upc: String?,
    @Json(name = "diamondCode") val diamondCode: String?,
    @Json(name = "ean") val ean: String?,
    @Json(name = "issn") val issn: String?,
    @Json(name = "format") val format: String?,
    @Json(name = "pageCount") val pageCount: Int?,
    @Json(name = "textObjects") val textObjects: List<ApiTextObject>?,
    @Json(name = "resourceURI") val resourceURI: String?,
    @Json(name = "urls") val urls: List<ApiUrl>?,
    @Json(name = "prices") val prices: List<ApiPrice>?,
    @Json(name = "thumbnail") val thumbnail: ApiThumbnail?,
    @Json(name = "images") val images: List<ApiComicImage>?
    ) : BaseNetworkApiResponse() {
    override fun errorCode(): String = ""
    override fun errorData(): String = ""
    override fun isSuccess(): Boolean = true
}

class ApiTextObject(
    @Json(name = "type") val type: String?,
    @Json(name = "language") val language: String?,
    @Json(name = "text") val text: String?
)

class ApiPrice(
    @Json(name = "type") val type: String?,
    @Json(name = "price") val price: String?
)

class ApiComicImage(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
)