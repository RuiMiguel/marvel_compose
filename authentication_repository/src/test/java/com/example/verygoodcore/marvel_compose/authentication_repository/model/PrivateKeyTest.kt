package com.example.verygoodcore.marvel_compose.authentication_repository.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PrivateKeyTest {
    @Test
    fun `can be instantiated`() {
        assertThat(PrivateKey("privateKey")).isNotNull
        assertThat(PrivateKey("")).isNotNull
    }
}