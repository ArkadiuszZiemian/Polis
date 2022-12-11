package com.avangarde.polis.configuration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

@JsonClass(generateAdapter = true)
class IdpWithPkce(
    @Json(name = "authorizationUri")
    override val authorizationUri: String,
    @Json(name = "tokenUri")
    override val tokenUri: String,
    @Json(name = "clientId")
    override val clientId: String,
    @Json(name = "clientSecret")
    override val clientSecret: String,
    @Json(name = "permissionScope")
    override val permissionScope: String,
    @Json(name = "codeChallengeMethod")
    val codeChallengeMethod: String,
    @Json(name = "messageDigestAlgorithm")
    val messageDigestAlgorithm: String
) : Idp() {

    fun getCodeVerifier(): String {
        val secureRandom = SecureRandom()
        val bytes = ByteArray(32)
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    fun getCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.toByteArray()
        val digest = MessageDigest.getInstance(messageDigestAlgorithm)
        val hash = digest.digest(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash)
    }
}
