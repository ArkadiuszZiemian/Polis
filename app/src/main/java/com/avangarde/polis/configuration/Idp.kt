package com.avangarde.polis.configuration

abstract class Idp {
    abstract val authorizationUri: String
    abstract val tokenUri: String
    abstract val clientId: String
    abstract val clientSecret: String
    abstract val permissionScope: String
    val redirectUri: String = "https://polis.avangarde.com/oauth2redirect"
}
