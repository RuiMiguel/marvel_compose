package com.codelabs.api_client.model

import com.squareup.moshi.Json

class ApiError(
    @Json(name = "code") val code: String? = null,
    @Json(name = "message") val message: String? = null
)