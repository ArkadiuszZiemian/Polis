package com.avangarde.polis.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class BaseIdp(
    @Json(name = "authorizationUri")
    override val authorizationUri: String,
    @Json(name = "tokenUri")
    override val tokenUri: String,
    @Json(name = "clientId")
    override val clientId: String,
    @Json(name = "clientSecret")
    override val clientSecret: String,
    @Json(name = "permissionScope")
    override val permissionScope: String
) : Idp()
