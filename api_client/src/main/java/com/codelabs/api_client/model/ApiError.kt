package com.codelabs.api_client.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiError(
    @Json(name = "code") val code: String? = null,
    @Json(name = "message") val message: String? = null
)