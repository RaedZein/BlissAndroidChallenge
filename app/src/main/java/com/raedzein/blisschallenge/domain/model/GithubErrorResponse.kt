package com.raedzein.blisschallenge.domain.model

import com.google.gson.annotations.SerializedName
import com.raedzein.blisschallenge.data.repositories.CustomErrorResponse

data class GithubErrorResponse(
    @SerializedName( "message") val message: String? = null,
    @SerializedName( "errors") val githubErrors: List<GithubErrorObject> = listOf()
): CustomErrorResponse() {
    override fun getError() = if (githubErrors.isNotEmpty()) githubErrors.joinToString(", ") { it.toString() }
    else message
}

data class GithubErrorObject(
    @SerializedName( "resource") val resource: String,
    @SerializedName( "field") val field: String,
    @SerializedName( "code") val code: String
) {
    override fun toString(): String {
        return "Resource: $resource, field:$field, code:$code"
    }
}
