package com.avangarde.polis

import com.avangarde.polis.configuration.IdpWithPkce
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class IdpWithPkceTest {

    private val idp = IdpWithPkce(
        "", "",
        "", "", "", "S256", "SHA-256"
    )
    private val pattern = "^[A-Za-z0-9\\-._~]+\$"

    @Test
    fun isCodeLengthInRange_true() {
        val min = 43
        val max = 128
        assertThat(idp.getCodeVerifier().length).isIn(min..max)
    }

    @Test
    fun isCodeCompatibleWithStandardPattern_true() {
        assertThat(idp.getCodeVerifier()).matches(pattern)
    }

    @Test
    fun isChallengeCompatibleWithStandardPattern_true() {
        assertThat(idp.getCodeChallenge(idp.getCodeVerifier())).matches(pattern)
    }
}
