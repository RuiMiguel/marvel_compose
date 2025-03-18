package com.codelabs.api_client.model

import com.squareup.moshi.Json

class ApiError(
    @Json(name = "code") val code: String? = null,
    @Json(name = "message") val message: String? = null
) : BaseNetworkApiResponse(
) {
    override fun errorCode(): String? = code ?: "-1"
    override fun errorData(): String? = message ?: "ERROR"
    override fun isSuccess(): Boolean = false
}