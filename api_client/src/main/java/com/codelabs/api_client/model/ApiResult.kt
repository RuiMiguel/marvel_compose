package com.codelabs.api_client.model

import com.squareup.moshi.Json

open class BaseNetworkApiResponse {
    open fun isSuccess(): Boolean = false
    open fun errorCode(): String? = null
    open fun errorData(): Any? = null
}

class ApiResult<T>(
    @Json(name = "code") val code: Int?,
    @Json(name = "status") val status: String?,
    @Json(name = "copyright") val copyright: String?,
    @Json(name = "attributionText") val attributionText: String?,
    @Json(name = "attributionHTML") val attributionHTML: String?,
    @Json(name = "data") val data: ApiData<T>?,
    @Json(name = "results") val results: String?,
) : BaseNetworkApiResponse() {
    override fun errorCode(): String = ""
    override fun errorData(): String = ""
    override fun isSuccess(): Boolean = true
}

class ApiData<T>(
    @Json(name = "offset") val offset: Int?,
    @Json(name = "limit") val limit: Int?,
    @Json(name = "total") val total: Int?,
    @Json(name = "count") val count: Int?,
    @Json(name = "results") val results: List<T>?,
)

class ApiUrl(
    @Json(name = "type") val type: String?,
    @Json(name = "url") val url: String?
)

class ApiThumbnail(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
)